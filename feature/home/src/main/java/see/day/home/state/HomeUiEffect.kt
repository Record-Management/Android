package see.day.home.state

import see.day.model.record.RecordType

sealed interface HomeUiEffect {
    data class onGoAddRecord(val recordType: RecordType): HomeUiEffect
}
