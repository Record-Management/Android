package see.day.home.state

import see.day.home.util.RecordFilterType
import see.day.model.record.RecordType

sealed interface HomeUiEvent {
    data object OnRefresh : HomeUiEvent
    data class OnClickSelectedDate(val year: Int, val month: Int) : HomeUiEvent
    data class OnClickFilterType(val filterType: RecordFilterType) : HomeUiEvent
    data class OnClickCell(val year: Int, val month: Int, val day: Int) : HomeUiEvent
    data class OnClickAddButton(val recordType: RecordType) : HomeUiEvent
    data class OnClickDetailButton(val recordType: RecordType, val recordId: String) : HomeUiEvent
    data object OnClickSetting : HomeUiEvent
}
