package see.day.setting.state.record

sealed interface RecordNotificationUiEvent {
    data object OnGoBack: RecordNotificationUiEvent
    data class OnChangedRecordNotification(
        val dailyRecordNotificationEnabled: Boolean? = null,
        val exerciseRecordNotificationEnabled: Boolean? = null,
        val habitRecordNotificationEnabled: Boolean? = null
    ) : RecordNotificationUiEvent
}
