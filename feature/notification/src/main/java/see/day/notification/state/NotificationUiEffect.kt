package see.day.notification.state

import see.day.model.record.RecordType

sealed interface NotificationUiEffect {
    data object NavigateToBackStack : NotificationUiEffect
    data class NavigateToWriteRecord(val recordType: RecordType) : NotificationUiEffect
    data object NavigateToResetGoal : NotificationUiEffect
}
