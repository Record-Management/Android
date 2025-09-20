package see.day.home.state

import see.day.model.record.RecordType

sealed interface HomeUiEffect {
    data class OnGoAddRecord(val recordType: RecordType) : HomeUiEffect
}
