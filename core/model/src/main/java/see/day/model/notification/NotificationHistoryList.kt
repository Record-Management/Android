package see.day.model.notification

data class NotificationHistoryList(
    val notifications: List<NotificationHistory>,
    val recentCheckedAt: String?
)
