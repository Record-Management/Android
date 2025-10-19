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
import see.day.model.record.habit.HabitRecordInput
import see.day.model.record.habit.HabitType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import see.day.network.HabitRecordService
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.HabitRecordResponse
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
            val habitRecordInput = HabitRecordInput(HabitType.SAVING, true, 10, 0, "","2024-10-19")
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
                isCompleted = false
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
}
