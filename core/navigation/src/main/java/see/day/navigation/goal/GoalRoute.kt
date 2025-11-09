package see.day.navigation.goal

import kotlinx.serialization.Serializable

sealed interface GoalRoute {

    @Serializable
    data class CurrentGoal(val userId: String) : GoalRoute
}
