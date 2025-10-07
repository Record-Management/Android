package see.day.exercise.util

import see.day.model.record.exercise.ExerciseType

sealed interface ExerciseRecordPostType {
    data class Edit(val id: String) : ExerciseRecordPostType
    data class Write(val exerciseType: ExerciseType) : ExerciseRecordPostType
}
