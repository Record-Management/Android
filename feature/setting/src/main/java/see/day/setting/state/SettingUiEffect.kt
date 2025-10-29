package see.day.setting.state

sealed interface SettingUiEffect {
    data object OnPopBack: SettingUiEffect
    data object OnGoGoalNotification: SettingUiEffect
    data object OnGoRecordNotification: SettingUiEffect
}
