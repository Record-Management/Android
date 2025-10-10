package see.day.navigation.exercise

import kotlinx.serialization.Serializable
import see.day.model.record.exercise.ExerciseType

sealed interface ExerciseRoute {

    @Serializable
    data object ExerciseSelect : ExerciseRoute

    @Serializable
    data class ExerciseWrite(val exerciseType: ExerciseType) : ExerciseRoute

    @Serializable
    data class ExerciseDetail(val recordId: String) : ExerciseRoute
}
