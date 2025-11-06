package see.day.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import see.day.domain.repository.HabitRecordRepository
import see.day.mapper.record.toDto
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitRecordEdit
import see.day.model.record.habit.HabitRecordInput
import see.day.model.record.habit.HabitType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import see.day.network.HabitRecordService
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.HabitRecordResponse
import see.day.network.dto.record.habit.HabitRecordCompleteRequest
import see.day.repository.HabitRecordRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class HabitRecordRepositoryTest {

    private lateinit var sut: HabitRecordRepository

    @Mock
    private lateinit var habitRecordService: HabitRecordService

    @Before
    fun setUp() {
        sut = HabitRecordRepositoryImpl(habitRecordService)
    }

    @Test
    fun givenHabitRecordInput_whenInserting_thenReturnsHabitRecords() {
        runTest {
            // given
            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val habitRecordInput = HabitRecordInput(HabitType.SAVING, true, 10, 0, "", "2024-10-19", false)
            val habitRecordResponse = HabitRecordResponse(
                id = "0",
                type = RecordType.HABIT.name,
                recordDate = timeFormatter.formatDate(),
                recordTime = timeFormatter.formatTime(),
                createdAt = "",
                updatedAt = "",
                habitType = HabitType.SAVING.name,
                notificationEnabled = true,
                notificationTime = "10:00:00",
                memo = "",
                isCompleted = false,
                isMainRecord = false
            )

            whenever(habitRecordService.postHabitRecord(habitRecordInput.toDto())).thenReturn(
                CommonResponse(
                    200,
                    "S20000",
                    "정상적으로 처리되었습니다.",
                    habitRecordResponse
                )
            )

            // when
            val result = sut.insertHabitRecord(habitRecordInput).getOrThrow()

            // then
            assertEquals(habitRecordInput.habitType, result.habitType)

            verify(habitRecordService).postHabitRecord(habitRecordInput.toDto())
        }
    }

    @Test
    fun givenRecordId_whenDeleting_thenWorksFine() {
        runTest {
            // given
            val recordId = "123213-123"

            whenever(habitRecordService.deleteHabitRecord(recordId)).thenReturn(
                CommonResponse(
                    200,
                    "S20000",
                    "정상적으로 처리되었습니다.",
                    null
                )
            )

            // when
            val result = sut.deleteHabitRecord(recordId).getOrThrow()

            // then
            verify(habitRecordService).deleteHabitRecord(recordId)
        }
    }

    @Test
    fun givenRecordIdAndIsCompleted_whenUpdateIsCompleted_thenWorksFine() {
        runTest {
            // given
            val recordId = "123-123"
            val isCompleted = true

            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val habitRecordResponse = HabitRecordResponse(
                id = "0",
                type = RecordType.HABIT.name,
                recordDate = timeFormatter.formatDate(),
                recordTime = timeFormatter.formatTime(),
                createdAt = "",
                updatedAt = "",
                habitType = HabitType.SAVING.name,
                notificationEnabled = true,
                notificationTime = "10:00:00",
                memo = "",
                isCompleted = true,
                isMainRecord = false
            )

            whenever(habitRecordService.updateHabitRecordComplete(recordId, HabitRecordCompleteRequest(isCompleted))).thenReturn(
                CommonResponse(
                    200,
                    "S20000",
                    "정상적으로 처리되었습니다.",
                    habitRecordResponse
                )
            )

            // when
            val result = sut.updateHabitRecordIsCompleted(recordId, isCompleted).getOrThrow()

            // then
            assertEquals(result, isCompleted)
            verify(habitRecordService).updateHabitRecordComplete(recordId, HabitRecordCompleteRequest(isCompleted))
        }
    }

    @Test
    fun givenRecordIdAndHabitRecordEditForm_whenUpdating_thenWorksFine() {
        runTest {
            // given
            val recordId = "123-123"
            val habitType = HabitType.SAVING
            val habitRecordEdit = HabitRecordEdit(habitType = habitType, notificationEnabled = false, hour = 9, minute = 0, memo = "", false)

            val timeFormatter = KoreanDateTimeFormatter(DateTime.now(DateTime.korea))
            val habitRecordResponse = HabitRecordResponse(
                id = "0",
                type = RecordType.HABIT.name,
                recordDate = timeFormatter.formatDate(),
                recordTime = timeFormatter.formatTime(),
                createdAt = "",
                updatedAt = "",
                habitType = habitType.name,
                notificationEnabled = false,
                notificationTime = "90:00:00",
                memo = "",
                isCompleted = true,
                isMainRecord = false
            )

            whenever(habitRecordService.updateHabitRecord(recordId, habitRecordEdit.toDto())).thenReturn(
                CommonResponse(
                    200,
                    "S20000",
                    "정상적으로 처리되었습니다.",
                    habitRecordResponse
                )
            )

            // when
            val result = sut.updateHabitRecord(recordId, habitRecordEdit).getOrThrow()

            // when
            assertEquals(result.habitType, habitRecordEdit.habitType)

            verify(habitRecordService).updateHabitRecord(recordId, habitRecordEdit.toDto())
        }
    }
}
