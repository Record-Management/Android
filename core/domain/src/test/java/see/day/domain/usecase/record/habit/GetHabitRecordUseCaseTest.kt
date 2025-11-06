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
import see.day.domain.repository.RecordRepository
import see.day.domain.repository.UserRepository
import see.day.model.calendar.HabitRecordDetail
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitType


@RunWith(MockitoJUnitRunner::class)
class GetHabitRecordUseCaseTest {


    @InjectMocks
    private lateinit var sut: GetHabitRecordUseCase

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var recordRepository: RecordRepository

    @Test
    fun givenRecordId_whenHabitRecordIsMainRecord_thenReturnCanBeMainFalse() = runTest {
        // given
        val recordId = "asdas"
        whenever(recordRepository.getRecord(recordId)).thenReturn(Result.success(HabitRecordDetail(recordId,RecordType.HABIT,"","","","",HabitType.EXERCISE,false,"09:30","",false,true)))
        // when
        val result = sut(recordId).getOrThrow()

        // then
        assertTrue(result.isMainRecord)
        assertFalse(result.canBeMain)

        verify(recordRepository).getRecord(recordId)
    }

    @Test
    fun givenRecordId_whenHabitRecordIsNotMainRecord_thenReturnCanBeMainFalse() = runTest {
        // given
        val recordId = "asdas"
        whenever(recordRepository.getRecord(recordId)).thenReturn(Result.success(HabitRecordDetail(recordId,RecordType.HABIT,"","","","",HabitType.EXERCISE,false,"09:30","",false,false)))
        whenever(userRepository.getMainRecordType()).thenReturn(Result.success(RecordType.EXERCISE))

        // when
        val result = sut(recordId).getOrThrow()

        // then
        assertFalse(result.isMainRecord)
        assertFalse(result.canBeMain)

        verify(recordRepository).getRecord(recordId)
        verify(userRepository).getMainRecordType()
    }

    @Test
    fun givenRecordId_whenHabitRecordIsMainRecord_thenReturnCanBeMainTrue() = runTest {
        // given
        val recordId = "asdas"
        whenever(recordRepository.getRecord(recordId)).thenReturn(Result.success(HabitRecordDetail(recordId,RecordType.HABIT,"","","","",HabitType.EXERCISE,false,"09:30","",false,false)))
        whenever(userRepository.getMainRecordType()).thenReturn(Result.success(RecordType.HABIT))

        // when
        val result = sut(recordId).getOrThrow()

        // then
        assertFalse(result.isMainRecord)
        assertTrue(result.canBeMain)

        verify(recordRepository).getRecord(recordId)
        verify(userRepository).getMainRecordType()
    }
}
