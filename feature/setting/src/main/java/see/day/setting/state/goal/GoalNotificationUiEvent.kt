package see.day.setting.state.goal

sealed interface GoalNotificationUiEvent {
    data object OnClickBack : GoalNotificationUiEvent
    data class OnGoalNotificationChanged(val enabled: Boolean) : GoalNotificationUiEvent
}
