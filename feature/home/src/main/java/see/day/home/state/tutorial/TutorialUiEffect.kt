package see.day.home.state.tutorial

sealed interface TutorialUiEffect {
    data object NavigateToHome : TutorialUiEffect
}
