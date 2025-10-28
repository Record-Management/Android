package see.day.setting.state.goal

data class GoalNotificationUiState(
    val goalNotificationEnabled: Boolean
) {
    companion object {
        val init = GoalNotificationUiState(
            goalNotificationEnabled = false
        )
    }
}
