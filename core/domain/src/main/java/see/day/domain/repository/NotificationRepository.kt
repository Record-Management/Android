package see.day.domain.repository

import see.day.model.notification.NotificationHistoryList
import see.day.model.notification.NotificationSettings
import see.day.model.notification.NotificationSettingsEdit

interface NotificationRepository {

    suspend fun getNotificationHistory() : Result<NotificationHistoryList>

    suspend fun updateNotificationHistoryAllRead() : Result<Unit>

    suspend fun getNotificationSetting() : Result<NotificationSettings>

    suspend fun updateNotificationSetting(notificationSettingsEdit: NotificationSettingsEdit) : Result<NotificationSettings>
}
