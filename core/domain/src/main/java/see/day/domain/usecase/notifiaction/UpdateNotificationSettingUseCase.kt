package see.day.domain.usecase.notifiaction

import see.day.domain.repository.NotificationRepository
import see.day.model.notification.NotificationSettings
import see.day.model.notification.NotificationSettingsEdit
import javax.inject.Inject

class UpdateNotificationSettingUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke(notificationSettingsEdit: NotificationSettingsEdit) : Result<NotificationSettings> {
        return notificationRepository.updateNotificationSetting(notificationSettingsEdit)
    }
}
