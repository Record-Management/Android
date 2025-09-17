package see.day.home.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import see.day.home.screen.toRecordType
import see.day.home.state.HomeUiEvent
import see.day.home.state.HomeUiState
import see.day.home.util.RecordFilterType
import see.day.model.calendar.DailyRecord
import see.day.model.calendar.DailyRecords
import see.day.model.calendar.MonthlyRecord
import see.day.model.date.CalendarDayInfo
import see.day.model.record.RecordType
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val monthlyRecord: MutableStateFlow<List<CalendarDayInfo>> = MutableStateFlow(listOf())

    // 얘는 날짜 정보와 월간
    fun onEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.OnClickSelectedDate -> {
                onClickSelectedDate(uiEvent.year, uiEvent.month)
            }

            is HomeUiEvent.OnClickFilterType -> {
                onClickFilterType(uiEvent.filterType)
            }

            is HomeUiEvent.OnClickCell -> {
                onClickCell(uiEvent.year, uiEvent.month, uiEvent.day)
            }
        }
    }

    private fun onClickSelectedDate(year: Int, month: Int) {
        // 데이터 다시 불러오기
        _uiState.update {
            it.copy(
                currentYear = year,
                currentMonth = month
            )
        }
    }

    private fun onClickFilterType(filterType: RecordFilterType) {
        if (filterType == uiState.value.selectedFilterType) {
            return
        }

        if (filterType == RecordFilterType.ALL) {
            _uiState.update {
                it.copy(
                    selectedFilterType = filterType,
                    monthlyRecords = monthlyRecord.value
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                selectedFilterType = filterType,
                monthlyRecords = it.monthlyRecords.filterMonthlyRecords(filterType.toRecordType() ?: throw IllegalStateException())

            )
        }
    }

    private fun onClickCell(year: Int, month: Int, day: Int) {
        if (uiState.value.currentYear != year || uiState.value.currentMonth != month) {
            onClickSelectedDate(year, month)
        }
        // 데이터 불러오기
        _uiState.update {
            it.copy(
                currentYear = year,
                currentMonth = month,
                selectedMonth = month,
                selectedDay = day
            )
        }
    }

    private fun List<CalendarDayInfo>.filterMonthlyRecords(recordType: RecordType): List<CalendarDayInfo> {
        return this.map {
            CalendarDayInfo(it.year, it.month, it.day, it.records.filter {
                it == recordType
            }, it.schedules)
        }
    }
}
