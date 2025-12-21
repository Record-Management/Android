package see.day.habit.state

sealed interface HabitDetailUiEffect {

    data class NavigateToHome(val isUpdated: Boolean = false): HabitDetailUiEffect
}
