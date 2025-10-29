package see.day.domain.usecase.notifiaction

import see.day.domain.repository.NotificationRepository
import see.day.model.notification.NotificationSettings
import javax.inject.Inject

class GetNotificationSettingUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke() : Result<NotificationSettings> {
        return notificationRepository.getNotificationSetting()
    }
}
