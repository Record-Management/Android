package see.day.setting.state.record

sealed interface RecordNotificationUiEffect {
    data object NavigateToBackStack : RecordNotificationUiEffect
}
