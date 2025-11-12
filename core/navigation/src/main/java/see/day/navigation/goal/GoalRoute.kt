package see.day.navigation.goal

import kotlinx.serialization.Serializable

sealed interface GoalRoute {

    @Serializable
    data object CurrentGoal : GoalRoute
}
