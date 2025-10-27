package see.day.domain.repository

import see.day.model.notification.NotificationHistoryList

interface NotificationRepository {

    suspend fun getNotificationHistory() : Result<NotificationHistoryList>

    suspend fun updateNotificationHistoryAllRead() : Result<Unit>
}
