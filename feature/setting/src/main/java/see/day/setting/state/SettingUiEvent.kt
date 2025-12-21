package see.day.setting.state

sealed interface SettingUiEvent {
    data object OnClickBack : SettingUiEvent
    data class OnChangedNickname(val nickname: String) : SettingUiEvent
    data class OnChangedBirthDate(val birthDate: String) : SettingUiEvent
    data object OnClickLogout : SettingUiEvent
    data object OnClickWithdrawal : SettingUiEvent
    data object OnClickGoalNotification : SettingUiEvent
    data object OnClickRecordNotification : SettingUiEvent
    data object OnClickDeleteCurrentGoal : SettingUiEvent
}
