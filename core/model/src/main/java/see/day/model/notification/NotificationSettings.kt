package see.day.model.notification

data class NotificationSettings(
    val userId: String,
    val dailyRecordEnabled: Boolean,
    val exerciseRecordEnabled: Boolean,
    val habitRecordEnabled: Boolean,
    val noGoalsNotificationEnabled: Boolean
)
