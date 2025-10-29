package see.day.repository

import see.day.domain.repository.NotificationRepository
import see.day.mapper.notification.toModel
import see.day.model.exception.NoDataException
import see.day.model.notification.NotificationHistoryList
import see.day.model.notification.NotificationSettings
import see.day.network.NotificationService
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationRepository {

    override suspend fun getNotificationHistory(): Result<NotificationHistoryList> {
        return createResult {
            notificationService.getNotificationHistory().data?.toModel() ?: throw NoDataException()
        }
    }

    override suspend fun updateNotificationHistoryAllRead(): Result<Unit> {
        return createResult {
            notificationService.updateNotificationHistoryAllRead()
        }
    }

    override suspend fun getNotificationSetting(): Result<NotificationSettings> {
        return createResult {
            notificationService.getNotificationSetting().data?.toModel() ?: throw NoDataException()
        }
    }
}
