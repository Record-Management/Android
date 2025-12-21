package see.day.setting.state

sealed interface SettingUiEvent {
    data object OnClickBack : SettingUiEvent
    data class OnNicknameChanged(val nickname: String) : SettingUiEvent
    data class OnBirthDateChanged(val birthDate: String) : SettingUiEvent
    data object OnClickLogout : SettingUiEvent
    data object OnClickWithdrawal : SettingUiEvent
    data object OnClickGoalNotification : SettingUiEvent
    data object OnClickRecordNotification : SettingUiEvent
    data object OnClickDeleteCurrentGoal : SettingUiEvent
}
