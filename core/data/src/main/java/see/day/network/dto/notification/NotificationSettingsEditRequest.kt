package see.day.network.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationSettingsEditRequest(
    val dailyRecordNotificationEnabled : Boolean?,
    val exerciseNotificationEnabled: Boolean?,
    val habitNotificationEnabled: Boolean?,
    val goalSettingNotificationEnabled: Boolean?,
//    val scheduleNotificationEnabled: Boolean? // [TODO] 이름 변경될 수 있음
)
