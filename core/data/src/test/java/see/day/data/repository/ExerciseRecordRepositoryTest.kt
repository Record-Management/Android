package see.day.data.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import see.day.domain.repository.ExerciseRecordRepository
import see.day.mapper.record.toDto
import see.day.model.exception.BadRequestException
import see.day.model.record.RecordType
import see.day.model.record.exercise.ExerciseRecordEdit
import see.day.model.record.exercise.ExerciseRecordInput
import see.day.model.record.exercise.ExerciseType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import see.day.network.ExerciseRecordService
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.ExerciseRecordResponse
import see.day.network.dto.toResponseBody
import see.day.repository.ExerciseRecordRepositoryImpl
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class ExerciseRecordRepositoryTest {

    private lateinit var sut: ExerciseRecordRepository

    @Mock
    private lateinit var exerciseRecordService: ExerciseRecordService

    @Before
    fun setUp() {
        sut = ExerciseRecordRepositoryImpl(exerciseRecordService)
    }

    @Test
    fun givenExerciseRecordInput_whenInserting_thenReturnsExerciseRecord() {
        runTest {
            // given
            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val exerciseRecordInput = ExerciseRecordInput(
                ExerciseType.GOLF,
                "as",
                recordDate = timeFormatter,
                caloriesBurned = "0",
                exerciseTimeMinutes = "0",
                stepCount = "0",
                weight = "0.5",
                imageUrls = listOf(),
            )
            val exerciseRecord = ExerciseRecordResponse(
                id = "0",
                type = RecordType.EXERCISE.name,
                recordDate = timeFormatter.formatDate(),
                recordTime = timeFormatter.formatTime(),
                createdAt = "",
                updatedAt = "",
                exerciseType = ExerciseType.GOLF.name,
                imageUrls = listOf(),
                exerciseTimeMinutes = 0,
                stepCount = 0,
                caloriesBurned = 0,
                weight = 0.5f,
                dailyNote = ""
            )

            whenever(exerciseRecordService.postExerciseRecord(exerciseRecordInput.toDto())).thenReturn(
                CommonResponse(
                    201,
                    "S201",
                    "운동기록이 성공적으로 작성되었습니다",
                    exerciseRecord
                )
            )

            // when
            val result = sut.insertExerciseRecord(exerciseRecordInput).getOrThrow()

            // then
            assertEquals(exerciseRecordInput.exerciseType, result.exerciseType)

            verify(exerciseRecordService).postExerciseRecord(exerciseRecordInput.toDto())
        }
    }

    @Test
    fun givenExerciseRecordInputWithoutSomeField_whenInserting_thenReturnsExerciseRecordWithoutSomeField() {
        runTest {
            // given
            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val exerciseRecordInput = ExerciseRecordInput(
                ExerciseType.GOLF,
                "as",
                recordDate = timeFormatter,
                caloriesBurned = "",
                exerciseTimeMinutes = "",
                stepCount = "0",
                weight = "0.5",
                imageUrls = listOf(),
            )
            val exerciseRecord = ExerciseRecordResponse(
                id = "0",
                type = RecordType.EXERCISE.name,
                recordDate = timeFormatter.formatDate(),
                recordTime = timeFormatter.formatTime(),
                createdAt = "",
                updatedAt = "",
                exerciseType = ExerciseType.GOLF.name,
                imageUrls = listOf(),
                exerciseTimeMinutes = null,
                stepCount = null,
                caloriesBurned = 0,
                weight = 0.5f,
                dailyNote = ""
            )

            whenever(exerciseRecordService.postExerciseRecord(exerciseRecordInput.toDto())).thenReturn(
                CommonResponse(
                    201,
                    "S201",
                    "운동기록이 성공적으로 작성되었습니다",
                    exerciseRecord
                )
            )

            // when
            val result = sut.insertExerciseRecord(exerciseRecordInput).getOrThrow()

            // then
            assertEquals(exerciseRecordInput.exerciseType, result.exerciseType)
            assertTrue(result.exerciseTimeMinutes.isEmpty())
            assertTrue(result.stepCount.isEmpty())
            verify(exerciseRecordService).postExerciseRecord(exerciseRecordInput.toDto())
        }
    }

    @Test
    fun givenExerciseRecordInput_whenInserting_thenThrows400Exception() {
        runTest {
            // given
            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val exerciseRecordInput = ExerciseRecordInput(
                ExerciseType.GOLF,
                "as",
                recordDate = timeFormatter,
                caloriesBurned = "",
                exerciseTimeMinutes = "",
                stepCount = "0",
                weight = "0.5",
                imageUrls = listOf(),
            )

            whenever(exerciseRecordService.postExerciseRecord(exerciseRecordInput.toDto())).thenThrow(
                HttpException(
                    Response.error<Any?>(
                        400,
                        toResponseBody<Unit?>(
                            CommonResponse(400, "E40408", "하루에 등록할 수 있는 운동 기록은 최대 1개입니다.", null)
                        )
                    )
                )
            )

            // when
            assertThrows(BadRequestException::class.java) {
                runBlocking {
                    sut.insertExerciseRecord(exerciseRecordInput)
                        .onFailure {
                            assertEquals("하루에 등록할 수 있는 운동 기록은 최대 1개입니다.",it.message)
                        }.getOrThrow()
                }
            }

            // then
            verify(exerciseRecordService).postExerciseRecord(exerciseRecordInput.toDto())
        }
    }

    @Test
    fun givenExerciseRecordEditForm_whenUpdating_thenWorksFine() {
        runTest {
            // given
            val recordId = "123123"
            val exerciseRecordEdit = ExerciseRecordEdit(recordId, ExerciseType.GOLF, "100","100","100","100.5","", listOf(),"")
            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val exerciseRecord = ExerciseRecordResponse(
                id = recordId,
                type = RecordType.EXERCISE.name,
                recordDate = timeFormatter.formatDate(),
                recordTime = timeFormatter.formatTime(),
                createdAt = "",
                updatedAt = "",
                exerciseType = ExerciseType.GOLF.name,
                imageUrls = listOf(),
                exerciseTimeMinutes = 100,
                stepCount = 100,
                caloriesBurned = 100,
                weight = 100.5f,
                dailyNote = ""
            )

            whenever(exerciseRecordService.updateExerciseRecord(recordId, exerciseRecordEdit.toDto())).thenReturn(
                CommonResponse(
                    200,
                    "S200",
                    "운동기록이 성공적으로 수정되었습니다",
                    exerciseRecord
                )
            )

            // when
            val result = sut.updateExerciseRecord(exerciseRecordEdit).getOrThrow()

            // then
            assertEquals(recordId,result.id)
            verify(exerciseRecordService).updateExerciseRecord(recordId, exerciseRecordEdit.toDto())
        }
    }
}
