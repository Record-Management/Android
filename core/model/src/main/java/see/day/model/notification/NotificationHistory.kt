package see.day.model.notification

import see.day.model.record.RecordType

data class NotificationHistory(
    val recordType: RecordType,
    val description: String,
    val sentAt: String
)
