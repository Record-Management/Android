package see.day.setting.state

sealed interface SettingUiEvent {
    data object OnPopBack: SettingUiEvent
    data class OnChangedNickname(val nickname: String) : SettingUiEvent
    data class OnChangedBirthDate(val birthDate: String) : SettingUiEvent
}
