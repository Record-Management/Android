package see.day.domain.repository

import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.record.exercise.ExerciseRecordEdit
import see.day.model.record.exercise.ExerciseRecordInput

interface ExerciseRecordRepository {

    suspend fun insertExerciseRecord(exerciseRecordInput: ExerciseRecordInput): Result<ExerciseRecordDetail>

    suspend fun updateExerciseRecord(exerciseRecordEdit: ExerciseRecordEdit): Result<ExerciseRecordDetail>

    suspend fun deleteExerciseRecord(recordId: String): Result<Unit>
}
