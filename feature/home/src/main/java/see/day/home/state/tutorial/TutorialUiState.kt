package see.day.home.state.tutorial

sealed interface TutorialUiState {
    data object Tutorial : TutorialUiState
    data object Loading : TutorialUiState
}
