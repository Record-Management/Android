package see.day.domain.usecase.record.habit

import see.day.domain.repository.HabitRecordRepository
import javax.inject.Inject

class UpdateHabitRecordIsCompletedUseCase @Inject constructor(
    private val habitRecordRepository: HabitRecordRepository
) {

    suspend operator fun invoke(recordId: String, isCompleted: Boolean) : Result<Boolean> {
        return habitRecordRepository.updateHabitRecordIsCompleted(recordId, isCompleted)
    }
}
