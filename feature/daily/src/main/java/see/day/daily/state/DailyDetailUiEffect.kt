package see.day.daily.state

interface DailyDetailUiEffect {

    data class OnPopHome(val isUpdated: Boolean = false) : DailyDetailUiEffect
}
