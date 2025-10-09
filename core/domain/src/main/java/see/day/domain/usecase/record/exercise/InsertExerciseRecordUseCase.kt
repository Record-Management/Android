package see.day.domain.usecase.record.exercise

import see.day.domain.repository.ExerciseRecordRepository
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.record.exercise.ExerciseRecordInput
import javax.inject.Inject

class InsertExerciseRecordUseCase @Inject constructor(
    private val exerciseRecordRepository: ExerciseRecordRepository
) {

    suspend operator fun invoke(exerciseRecordInput: ExerciseRecordInput) : Result<ExerciseRecordDetail> {
        return exerciseRecordRepository.insertExerciseRecord(exerciseRecordInput)
    }
}
