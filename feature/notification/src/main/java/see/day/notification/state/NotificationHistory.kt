package see.day.notification.state

import see.day.model.record.RecordType

data class NotificationHistory(
    val recordType: RecordType,
    val relativeTime: String,
    val isChecked: Boolean
)
