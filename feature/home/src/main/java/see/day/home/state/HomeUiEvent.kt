package see.day.home.state

import see.day.home.util.RecordFilterType

sealed interface HomeUiEvent {
    data class OnClickSelectedDate(val year: Int, val month: Int) : HomeUiEvent
    data class OnClickFilterType(val filterType: RecordFilterType) : HomeUiEvent
    data class OnClickCell(val year: Int, val month: Int, val day: Int) : HomeUiEvent
}
