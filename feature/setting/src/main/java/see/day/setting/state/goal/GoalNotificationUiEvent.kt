package see.day.setting.state.goal

sealed interface GoalNotificationUiEvent {
    data object OnGoBack: GoalNotificationUiEvent
    data class OnChangedGoalNotification(val enabled: Boolean) : GoalNotificationUiEvent
}
