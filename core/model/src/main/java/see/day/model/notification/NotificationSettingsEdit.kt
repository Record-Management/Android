package see.day.model.notification

data class NotificationSettingsEdit(
    val dailyRecordNotificationEnabled : Boolean? = null,
    val exerciseNotificationEnabled: Boolean? = null,
    val habitNotificationEnabled: Boolean? = null,
    val noGoalNotificationEnabled: Boolean? = null
)
