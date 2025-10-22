package see.day.setting.state

sealed interface SettingUiEffect {
    data object OnPopBack: SettingUiEffect
}
