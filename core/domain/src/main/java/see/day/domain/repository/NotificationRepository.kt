package see.day.domain.repository

import see.day.model.notification.NotificationHistoryList
import see.day.model.notification.NotificationSettings

interface NotificationRepository {

    suspend fun getNotificationHistory() : Result<NotificationHistoryList>

    suspend fun updateNotificationHistoryAllRead() : Result<Unit>

    suspend fun getNotificationSetting() : Result<NotificationSettings>
}
