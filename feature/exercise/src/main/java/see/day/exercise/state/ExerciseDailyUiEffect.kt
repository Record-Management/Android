package see.day.exercise.state

sealed interface ExerciseDailyUiEffect {

    data class NavigateToHome(val isUpdated: Boolean = false) : ExerciseDailyUiEffect
}
