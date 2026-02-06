package see.day.home.state.tutorial

sealed interface TutorialUiEvent {
    data object OnClickCloseButton : TutorialUiEvent
}
