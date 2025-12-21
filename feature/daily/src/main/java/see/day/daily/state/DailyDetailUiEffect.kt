package see.day.daily.state

interface DailyDetailUiEffect {

    data class NavigateToHome(val isUpdated: Boolean = false) : DailyDetailUiEffect
}
