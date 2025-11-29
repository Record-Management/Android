package see.day.home.state

import see.day.model.record.RecordType

sealed interface HomeUiEffect {
    data class OnGoAddRecord(val recordType: RecordType) : HomeUiEffect
    data class OnGoDetailRecord(val recordType: RecordType, val recordId: String) : HomeUiEffect
    data object OnGoSetting : HomeUiEffect
    data object OnGoNotification : HomeUiEffect
    data object OnGoCurrentGoal : HomeUiEffect
    data object OnGoSetNewGoal : HomeUiEffect
}
