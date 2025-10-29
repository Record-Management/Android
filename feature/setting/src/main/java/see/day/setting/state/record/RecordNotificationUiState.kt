package see.day.setting.state.record

data class RecordNotificationUiState(
    val dailyRecordNotificationEnabled: Boolean,
    val exerciseRecordNotificationEnabled: Boolean,
    val habitRecordNotificationEnabled: Boolean
) {
    fun isAllNotificationEnabled() = dailyRecordNotificationEnabled && exerciseRecordNotificationEnabled && habitRecordNotificationEnabled

    companion object {
        val init = RecordNotificationUiState(false, false, false)
    }
}
