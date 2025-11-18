package see.day.notification.state

import see.day.model.record.RecordType

sealed interface NotificationUiEffect {
    data object OnPopBack: NotificationUiEffect
    data class GoWriteRecord(val recordType: RecordType) : NotificationUiEffect
    data object OnResetGoal: NotificationUiEffect
}
