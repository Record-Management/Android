package see.day.setting.state.record

sealed interface RecordNotificationUiEvent {
    data object OnClickBack : RecordNotificationUiEvent
    data class OnRecordNotificationChanged(
        val dailyRecordNotificationEnabled: Boolean? = null,
        val exerciseRecordNotificationEnabled: Boolean? = null,
        val habitRecordNotificationEnabled: Boolean? = null
    ) : RecordNotificationUiEvent
}
