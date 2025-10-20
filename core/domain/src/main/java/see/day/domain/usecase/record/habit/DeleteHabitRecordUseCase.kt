package see.day.domain.usecase.record.habit

import see.day.domain.repository.HabitRecordRepository
import javax.inject.Inject

class DeleteHabitRecordUseCase @Inject constructor(
    private val habitRecordRepository: HabitRecordRepository
) {

    suspend operator fun invoke(recordId: String) : Result<Unit> {
        return habitRecordRepository.deleteHabitRecord(recordId)
    }
}
