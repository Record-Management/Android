package see.day.goal.state.reset

sealed interface ResetGoalUiEffect {
    data object OnBack: ResetGoalUiEffect
    data object OnFinishApp: ResetGoalUiEffect
}
