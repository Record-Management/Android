package see.day.network.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleInputRequest(
    val title: String,
    val startDate: String,
    val endDate: String,
    val notificationType: String,
    val notificationCustomHours: Int?,
    val notificationCustomMinutes: Int?,
    val repeatType: String,
    val repeatEndsOn: String?,
    val location: String? = null,
    val color: String,
    val memo: String? = null,
)
