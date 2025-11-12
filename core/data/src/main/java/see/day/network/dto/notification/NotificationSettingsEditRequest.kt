package see.day.network.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationSettingsEditRequest(
    val dailyRecordNotificationEnabled : Boolean?,
    val exerciseNotificationEnabled: Boolean?,
    val habitNotificationEnabled: Boolean?,
    val goalSettingNotificationEnabled: Boolean?
)
