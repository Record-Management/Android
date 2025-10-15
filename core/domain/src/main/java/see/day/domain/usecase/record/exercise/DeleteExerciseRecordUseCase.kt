package see.day.domain.usecase.record.exercise

import see.day.domain.repository.ExerciseRecordRepository
import javax.inject.Inject

class DeleteExerciseRecordUseCase @Inject constructor(
    private val exerciseRecordRepository: ExerciseRecordRepository
) {

    suspend operator fun invoke(recordId: String) : Result<Unit> {
        return exerciseRecordRepository.deleteExerciseRecord(recordId)
    }
}
