package see.day.domain.usecase.notifiaction

import see.day.domain.repository.NotificationRepository
import javax.inject.Inject

class UpdateNotificationHistoryAllReadUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke() : Result<Unit> {
        return notificationRepository.updateNotificationHistoryAllRead()
    }
}
