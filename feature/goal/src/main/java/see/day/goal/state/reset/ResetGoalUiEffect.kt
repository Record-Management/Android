package see.day.goal.state.reset

sealed interface ResetGoalUiEffect {
    data object NavigateToBack: ResetGoalUiEffect
    data object NavigateToFinishResetGoal: ResetGoalUiEffect
}
