package see.day.notification.state

import see.day.model.notification.NotificationType

data class NotificationHistoryUiModel(
    val notificationType: NotificationType,
    val title: String,
    val message: String,
    val relativeTime: String,
    val isChecked: Boolean
) {

    fun isVisible(): Boolean {
        return notificationType != NotificationType.TEST && notificationType != NotificationType.SYSTEM_ANNOUNCEMENT
    }
}
