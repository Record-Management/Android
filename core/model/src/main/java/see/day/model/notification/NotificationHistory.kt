package see.day.model.notification

data class NotificationHistory(
    val id: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val sentAt: String,
    val isRead: Boolean
)
