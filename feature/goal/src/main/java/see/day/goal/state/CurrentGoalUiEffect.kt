package see.day.goal.state

sealed interface CurrentGoalUiEffect {
    data object OnGoBack: CurrentGoalUiEffect
    data object OnGoGoalSetting: CurrentGoalUiEffect
}
