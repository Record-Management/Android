package see.day.domain.repository

import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.record.exercise.ExerciseRecordInput

interface ExerciseRecordRepository {

    suspend fun insertExerciseRecord(exerciseRecordInput: ExerciseRecordInput) : Result<ExerciseRecordDetail>
}
