package see.day.goal.state

sealed interface CurrentGoalUiEffect {
    data object NavigateToBack: CurrentGoalUiEffect
    data object NavigateToResetGoalSetting: CurrentGoalUiEffect
}
