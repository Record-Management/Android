package see.day.data.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import see.day.domain.repository.DailyRecordRepository
import see.day.mapper.record.toDto
import see.day.model.exception.BadRequestException
import see.day.model.record.daily.DailyRecordInput
import see.day.model.record.daily.DailyEmotion
import see.day.model.record.daily.DailyRecordEdit
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import see.day.network.DailyRecordService
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.DailyRecordResponse
import see.day.network.dto.toResponseBody
import see.day.repository.DailyRecordRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class DailyRecordRepositoryTest {

    private lateinit var sut: DailyRecordRepository

    @Mock
    private lateinit var dailyRecordService: DailyRecordService

    @Before
    fun setUp() {
        sut = DailyRecordRepositoryImpl(dailyRecordService)
    }

    @Test
    fun givenCreateDailyRecord_whenInsertDailyRecord_thenReturnsRegisteredDailyRecord() {
        runTest {
            // given
            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val dailyRecordInput = DailyRecordInput("", DailyEmotion.Sad, timeFormatter, listOf())
            val registeredDailyRecordResponse = DailyRecordResponse(id = "", type = "DAILY", emotion = "Sad", recordTime = "", imageUrls = listOf(), content = "", createdAt = "", updatedAt = "", recordDate = "")

            whenever(dailyRecordService.postDailyRecord(dailyRecordInput.toDto())).thenReturn(
                CommonResponse(
                    201,
                    "S201",
                    "하루 기록이 정상적으로 작성되었습니다",
                    registeredDailyRecordResponse
                )
            )

            // when
            val result = sut.insertRecord(dailyRecordInput).getOrThrow()

            // then
            assertEquals(dailyRecordInput.content, result.content)
            assertEquals(dailyRecordInput.imageUrls, result.imageUrls)

            verify(dailyRecordService).postDailyRecord(dailyRecordInput.toDto())
        }
    }

    @Test
    fun givenOverTwoCreateDailyRecord_whenInsertDailyRecord_thenThrows400Exception() {
        runTest {
            // given
            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val dailyRecordInput = DailyRecordInput("", DailyEmotion.Sad, timeFormatter, listOf())

            whenever(dailyRecordService.postDailyRecord(dailyRecordInput.toDto())).thenThrow(
                HttpException(
                    Response.error<Any?>(
                        400,
                        toResponseBody<Unit?>(CommonResponse(400, "E40407", "하루에 등록할 수 있는 일상 기록은 최대 2개입니다.", null))
                    )
                )
            )

            // when
            assertThrows(BadRequestException::class.java) {
                runBlocking {
                    sut.insertRecord(dailyRecordInput)
                        .onFailure {
                            assertEquals("하루에 등록할 수 있는 일상 기록은 최대 2개입니다.", it.message)
                        }.getOrThrow()
                }
            }

            // then
            verify(dailyRecordService).postDailyRecord(dailyRecordInput.toDto())
        }
    }

    @Test
    fun givenRecordEditForm_whenUpdate_thenWorksFine() {
        runTest {
            // given
            val recordId = "123123"
            val dailyRecordEdit = DailyRecordEdit(recordId, "",DailyEmotion.Sad, listOf())
            val dailyRecordResponse = DailyRecordResponse(id = "", type = "DAILY", emotion = "Sad", recordTime = "", imageUrls = listOf(), content = "", createdAt = "", updatedAt = "", recordDate = "")

            whenever(dailyRecordService.updateDailyRecord(recordId, dailyRecordEdit.toDto())).thenReturn(
                CommonResponse(
                    200,
                    "S200",
                    "하루 기록이 성공적으로 수정되었습니다.",
                    dailyRecordResponse
                )
            )

            // when
            val result = sut.updateRecord(dailyRecordEdit).getOrThrow()

            // then
            verify(dailyRecordService).updateDailyRecord(recordId, dailyRecordEdit.toDto())
        }
    }

    @Test
    fun givenRecordId_whenDelete_thenWorksFine() {
        runTest {
            // given
            val recordId = "!23123"

            whenever(dailyRecordService.deleteDailyRecord(recordId)).thenReturn(
                CommonResponse(
                    200,
                    "S200",
                    "하루 기록이 정상적으로 삭제되었습니다",
                    null
                )
            )

            // when
            val result = sut.deleteRecord(recordId).getOrThrow()

            // then
            assertEquals(Unit, result)
            verify(dailyRecordService).deleteDailyRecord(recordId)
        }
    }
}
