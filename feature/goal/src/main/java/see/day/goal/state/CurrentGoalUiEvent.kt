package see.day.goal.state

sealed interface CurrentGoalUiEvent {
    data object OnClickBack: CurrentGoalUiEvent
    data object OnClickGoalBanner: CurrentGoalUiEvent
}
