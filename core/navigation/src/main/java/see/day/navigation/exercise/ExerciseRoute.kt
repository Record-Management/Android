package see.day.navigation.exercise

import kotlinx.serialization.Serializable

sealed interface ExerciseRoute {

    @Serializable
    data object Exercise : ExerciseRoute
}
