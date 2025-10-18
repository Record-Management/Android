package see.day.navigation.habit

import kotlinx.serialization.Serializable
import see.day.model.record.habit.HabitType

sealed interface HabitRoute {

    @Serializable
    data object HabitSelect : HabitRoute

    @Serializable
    data class HabitWrite(val habitType: HabitType) : HabitRoute
}
