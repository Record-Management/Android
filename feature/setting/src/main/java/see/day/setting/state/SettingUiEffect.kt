package see.day.setting.state

sealed interface SettingUiEffect {
    data object NavigateToBackStack : SettingUiEffect
    data object NavigateToGoalNotificationSetting : SettingUiEffect
    data object NavigateToRecordNotificationSetting : SettingUiEffect
}
