package see.day.notification.state

import see.day.model.record.RecordType

sealed interface NotificationUiEvent {
    data object OnClickBack : NotificationUiEvent
    data class OnClickItem(val recordType: RecordType, val relativeTime: String) : NotificationUiEvent
    data object OnClickResetGoalBanner : NotificationUiEvent
}
