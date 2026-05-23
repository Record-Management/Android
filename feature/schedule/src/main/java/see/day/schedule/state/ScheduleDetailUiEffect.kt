package see.day.schedule.state

sealed interface ScheduleDetailUiEffect {
    data class NavigateToHome(val isUpdated: Boolean = false): ScheduleDetailUiEffect
}
