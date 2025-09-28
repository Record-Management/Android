package see.day.navigation.schedule

import kotlinx.serialization.Serializable

sealed interface ScheduleRoute {

    @Serializable
    data object Schedule : ScheduleRoute
}
