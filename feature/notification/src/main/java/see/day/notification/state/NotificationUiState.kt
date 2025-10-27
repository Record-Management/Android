package see.day.notification.state

data class NotificationUiState(
    val recentCheckedAt: String,
    val notificationHistories: List<NotificationHistoryUiModel>
) {
    companion object {
        val init = NotificationUiState("", listOf())
    }
}
