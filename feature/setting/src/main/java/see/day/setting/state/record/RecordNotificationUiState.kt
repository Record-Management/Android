package see.day.setting.state.record

data class RecordNotificationUiState(
    val dailyRecordNotificationEnabled: Boolean,
    val exerciseRecordNotificationEnabled: Boolean,
    val habitRecordNotificationEnabled: Boolean,
    val scheduleNotificationEnabled: Boolean // [TODO] 이름 변경될 수 있음
) {
    fun isAllNotificationEnabled() = dailyRecordNotificationEnabled && exerciseRecordNotificationEnabled && habitRecordNotificationEnabled && scheduleNotificationEnabled

    companion object {
        val init = RecordNotificationUiState(false, false, false, false)
    }
}
