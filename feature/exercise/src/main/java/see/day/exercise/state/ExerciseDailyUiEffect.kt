package see.day.exercise.state

sealed interface ExerciseDailyUiEffect {

    data class OnPopHome(val isUpdated: Boolean = false) : ExerciseDailyUiEffect
}
