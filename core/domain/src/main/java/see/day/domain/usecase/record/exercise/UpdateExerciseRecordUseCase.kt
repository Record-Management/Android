package see.day.domain.usecase.record.exercise

import see.day.domain.repository.ExerciseRecordRepository
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.record.exercise.ExerciseRecordEdit
import javax.inject.Inject

class UpdateExerciseRecordUseCase @Inject constructor(
    private val exerciseRecordRepository: ExerciseRecordRepository
) {

    suspend operator fun invoke(exerciseRecordEdit: ExerciseRecordEdit): Result<ExerciseRecordDetail> {
        return exerciseRecordRepository.updateExerciseRecord(exerciseRecordEdit)
    }
}
