package see.day.network.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationSettingResponse(
    val userId: String,
    val dailyRecordNotificationEnabled: Boolean,
    val exerciseNotificationEnabled: Boolean,
    val habitNotificationEnabled: Boolean,
    val goalSettingNotificationEnabled: Boolean
)
