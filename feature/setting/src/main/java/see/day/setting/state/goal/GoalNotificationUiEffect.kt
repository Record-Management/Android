package see.day.setting.state.goal

sealed interface GoalNotificationUiEffect {
    data object OnGoBack: GoalNotificationUiEffect
}
