package see.day.repository

import see.day.domain.repository.ExerciseRecordRepository
import see.day.mapper.record.toDto
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.exception.NoDataException
import see.day.model.record.exercise.ExerciseRecordInput
import see.day.network.ExerciseRecordService
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject

class ExerciseRecordRepositoryImpl @Inject constructor(
    private val exerciseRecordService: ExerciseRecordService
) : ExerciseRecordRepository {

    override suspend fun insertExerciseRecord(exerciseRecordInput: ExerciseRecordInput): Result<ExerciseRecordDetail> {
        return createResult {
            exerciseRecordService.postExerciseRecord(exerciseRecordInput.toDto()).data?.toExerciseRecord() ?: throw NoDataException()
        }
    }
}
