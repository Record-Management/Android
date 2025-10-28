package see.day.domain.usecase.notifiaction

import see.day.domain.repository.NotificationRepository
import see.day.model.notification.NotificationHistory
import see.day.model.notification.NotificationHistoryList
import javax.inject.Inject

class GetNotificationHistoryUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke(): Result<NotificationHistoryList> {
        return notificationRepository.getNotificationHistory()
    }

}
