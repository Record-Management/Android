package see.day.notification.state

import see.day.model.calendar.RecordDetail
import see.day.model.record.RecordType

data class NotificationUiState(
    val recentCheckedAt: String,
    val notificationHistories: List<NotificationHistoryUiModel>,
    val todayRecords: List<RecordDetail>,
    val mainRecordType: RecordType
) {
    companion object {
        val init = NotificationUiState("", listOf(), listOf(), RecordType.DAILY)
    }
}
