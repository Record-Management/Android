package see.day.navigation.habit

import kotlinx.serialization.Serializable

sealed interface HabitRoute {

    @Serializable
    data object HabitSelect : HabitRoute
}
