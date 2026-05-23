package see.day.navigation.schedule

import kotlinx.serialization.Serializable
import java.io.Serial

sealed interface ScheduleRoute {

    @Serializable
    data object ScheduleWrite : ScheduleRoute

    @Serializable
    data class ScheduleDetail(val recordId: String) : ScheduleRoute
}
