package see.day.home.state

import see.day.model.record.RecordType

sealed interface HomeUiEffect {
    data class NavigateToAddRecord(val recordType: RecordType) : HomeUiEffect
    data class NavigateToDetailRecord(val recordType: RecordType, val recordId: String) : HomeUiEffect
    data object NavigateToSetting : HomeUiEffect
    data object NavigateToNotification : HomeUiEffect
    data object NavigateToCurrentGoal : HomeUiEffect
    data object NavigateToResetGoal : HomeUiEffect
    data object NavigateToTutorial : HomeUiEffect
}
