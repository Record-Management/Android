package see.day.domain.usecase.record.habit

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import see.day.domain.repository.CalendarRepository
import see.day.domain.repository.UserRepository
import see.day.model.calendar.DailyRecordDetails
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.calendar.HabitRecordDetail
import see.day.model.record.RecordType
import see.day.model.record.exercise.ExerciseType
import see.day.model.record.habit.HabitType

@RunWith(MockitoJUnitRunner::class)
class CanSetAsMainRecordUseCaseTest {

    @InjectMocks
    private lateinit var sut: CanSetAsMainRecordUseCase

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var calendarRepository: CalendarRepository

    @Test
    fun givenIsNotMainTypeHabit_when_thenReturnsFalse() = runTest {
        // given
        whenever(userRepository.getMainRecordType()).thenReturn(Result.success(RecordType.DAILY))

        // when
        val result = sut("2025-11-06").getOrThrow()

        // then
        assertFalse(result)

        verify(userRepository).getMainRecordType()
    }

    @Test
    fun givenIsHabitTypeAndEmptyDailyRecords_when_thenReturnsFalse() = runTest {
        // given
        val date = "2025-11-06"
        whenever(userRepository.getMainRecordType()).thenReturn(Result.success(RecordType.HABIT))
        whenever(calendarRepository.getDailyRecords(date)).thenReturn(Result.success(DailyRecordDetails(date, listOf())))

        // when
        val result = sut(date).getOrThrow()

        // then
        assertFalse(result)

        verify(userRepository).getMainRecordType()
        verify(calendarRepository).getDailyRecords(date)
    }

    @Test
    fun givenDailyRecordsWithOutHabit_when_thenReturnsFalse() = runTest {
        // given
        val date = "2025-11-06"
        whenever(userRepository.getMainRecordType()).thenReturn(Result.success(RecordType.HABIT))
        whenever(calendarRepository.getDailyRecords(date)).thenReturn(Result.success(DailyRecordDetails(date, listOf(ExerciseRecordDetail("",RecordType.EXERCISE,"","","","",ExerciseType.YOGA, listOf(),"","","","","")))))

        // when
        val result = sut(date).getOrThrow()

        // then
        assertFalse(result)

        verify(userRepository).getMainRecordType()
        verify(calendarRepository).getDailyRecords(date)
    }

    @Test
    fun givenHabitDailyRecords_when_thenReturnsTrue() = runTest {
        // given
        val date = "2025-11-06"
        whenever(userRepository.getMainRecordType()).thenReturn(Result.success(RecordType.HABIT))
        whenever(calendarRepository.getDailyRecords(date)).thenReturn(Result.success(DailyRecordDetails(date, listOf(HabitRecordDetail("",RecordType.HABIT,"","","","",HabitType.EXERCISE,false,"","",false,true)))))

        // when
        val result = sut(date).getOrThrow()

        // then
        assertTrue(result)

        verify(userRepository).getMainRecordType()
        verify(calendarRepository).getDailyRecords(date)
    }
}
