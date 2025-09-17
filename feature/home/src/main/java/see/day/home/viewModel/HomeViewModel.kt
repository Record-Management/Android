package see.day.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import see.day.domain.usecase.calendar.GetDetailDailyRecordsUseCase
import see.day.domain.usecase.calendar.GetMonthlyRecordsUseCase
import see.day.domain.usecase.user.GetUserRecordTypeUseCase
import see.day.home.screen.toRecordType
import see.day.home.state.HomeUiEvent
import see.day.home.state.HomeUiState
import see.day.home.util.RecordFilterType
import see.day.model.date.CalendarDay
import see.day.model.date.CalendarDayInfo
import see.day.model.record.RecordType
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserRecordTypeUseCase: GetUserRecordTypeUseCase,
    private val getMonthlyRecordsUseCase: GetMonthlyRecordsUseCase,
    private val getDetailDailyRecordsUseCase: GetDetailDailyRecordsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val monthlyRecord: MutableStateFlow<List<CalendarDayInfo>> = MutableStateFlow(listOf())

    init {
        viewModelScope.launch {
            val state = uiState.value
            try {
                val recordType = async { getUserRecordTypeUseCase().getOrThrow() }
                val monthlyRecords = async { getMonthlyRecordsUseCase(state.currentYear, state.currentMonth, arrayOf()).getOrThrow() }
                val detailDailyRecords = getDetailDailyRecordsUseCase(HomeUiState.getTodayDate()).getOrThrow()

                val calendarDayInfos = CalendarDayInfo.of(monthlyRecords.await())
                monthlyRecord.update {
                    calendarDayInfos
                }

                _uiState.update {
                    it.copy(
                        mainRecordType = recordType.await(),
                        monthlyRecords = calendarDayInfos,
                        dailyDetailRecords = detailDailyRecords
                    )
                }
            } catch (e: Exception) {

            }

        }

    }

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
        viewModelScope.launch {
            getMonthlyRecordsUseCase(year, month, arrayOf())
                .onSuccess {
                    val calendarDayInfos = CalendarDayInfo.of(it)
                    monthlyRecord.update {
                        calendarDayInfos
                    }
                    _uiState.update {
                        it.copy(
                            currentYear = year,
                            currentMonth = month,
                            monthlyRecords = calendarDayInfos
                        )
                    }
                }
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

        viewModelScope.launch {
            getDetailDailyRecordsUseCase("$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}")
                .onSuccess { dailyRecords ->
                    _uiState.update {
                        it.copy(
                            currentYear = year,
                            currentMonth = month,
                            selectedMonth = month,
                            selectedDay = day,
                            dailyDetailRecords = dailyRecords
                        )
                    }
                }.onFailure {

                }

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
