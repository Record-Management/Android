package see.day.habit.state

sealed interface HabitDetailUiEffect {

    data class OnPopHome(val isUpdated: Boolean = false): HabitDetailUiEffect
}
