package see.day.setting.state.goal

sealed interface GoalNotificationUiEffect {
    data object NavigateToBackStack : GoalNotificationUiEffect
}
