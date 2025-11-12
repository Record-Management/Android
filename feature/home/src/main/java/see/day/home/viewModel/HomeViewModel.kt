package see.day.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import see.day.domain.usecase.calendar.GetDailyRecordsUseCase
import see.day.domain.usecase.calendar.GetMonthlyRecordsUseCase
import see.day.domain.usecase.goal.GetCurrentGoalUseCase
import see.day.domain.usecase.record.daily.DeleteDailyRecordUseCase
import see.day.domain.usecase.record.exercise.DeleteExerciseRecordUseCase
import see.day.domain.usecase.record.habit.DeleteHabitRecordUseCase
import see.day.domain.usecase.record.habit.UpdateHabitRecordIsCompletedUseCase
import see.day.domain.usecase.user.GetStoredDateUseCase
import see.day.domain.usecase.user.GetUserUseCase
import see.day.domain.usecase.user.UpdateStoredDateUseCase
import see.day.home.screen.toRecordType
import see.day.home.state.HomeUiEffect
import see.day.home.state.HomeUiEvent
import see.day.home.state.HomeUiState
import see.day.home.util.RecordFilterType
import see.day.model.calendar.HabitRecordDetail
import see.day.model.date.CalendarDayInfo
import see.day.model.record.RecordType
import see.day.navigation.home.Home
import java.time.LocalDate

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getMonthlyRecordsUseCase: GetMonthlyRecordsUseCase,
    private val getDailyRecordsUseCase: GetDailyRecordsUseCase,
    private val deleteDailyRecordUseCase: DeleteDailyRecordUseCase,
    private val deleteExerciseRecordUseCase: DeleteExerciseRecordUseCase,
    private val deleteHabitRecordUseCase: DeleteHabitRecordUseCase,
    private val updateHabitRecordIsCompletedUseCase: UpdateHabitRecordIsCompletedUseCase,
    private val getCurrentGoalUseCase: GetCurrentGoalUseCase,
    private val getStoredDateUseCase: GetStoredDateUseCase,
    private val updateStoredDateUseCase: UpdateStoredDateUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<HomeUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<HomeUiEffect> = _uiEffect.asSharedFlow()

    private val _toastMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    private val monthlyRecord: MutableStateFlow<List<CalendarDayInfo>> = MutableStateFlow(listOf())

    init {
        viewModelScope.launch {
            val state = uiState.value
            try {
                val user = async { getUserUseCase().getOrThrow() }
                val monthlyRecords = async { getMonthlyRecordsUseCase(state.currentYear, state.currentMonth, arrayOf()).getOrThrow() }
                val detailDailyRecords = getDailyRecordsUseCase(HomeUiState.getTodayDate()).getOrThrow()
                val currentGoal = getCurrentGoalUseCase(user.await().id).getOrThrow()


                val calendarDayInfos = CalendarDayInfo.of(monthlyRecords.await())
                monthlyRecord.update {
                    calendarDayInfos
                }


                _uiState.update {
                    it.copy(
                        userId = user.await().id,
                        mainRecordType = user.await().mainRecordType,
                        goalDays = user.await().goalDays,
                        monthlyRecords = calendarDayInfos,
                        dailyRecordDetails = detailDailyRecords,
                        createdAt = user.await().createdAt,
                        todayRecords = detailDailyRecords,
                        treeStage = currentGoal?.treeStage,
                        shouldCreateNewGoal = currentGoal != null
                    )
                }

                val storedDateString = getStoredDateUseCase().getOrThrow()
                val todayDate = LocalDate.parse(HomeUiState.getTodayDate())

                if(currentGoal == null) {
                    if(storedDateString != null) {
                        val storedDate = LocalDate.parse(storedDateString)
                        if(storedDate < todayDate) {
                            updateStoredDateUseCase(HomeUiState.getTodayDate())
                        }
                    }
                    return@launch
                }

                if(storedDateString == null) {
                    if(currentGoal.canCreateNew) {
                        _uiEffect.emit(HomeUiEffect.OnGoCurrentGoal(user.await().id))
                    }
                    updateStoredDateUseCase(HomeUiState.getTodayDate())
                    return@launch
                }

                val storedDate = LocalDate.parse(storedDateString)
                val endDate = LocalDate.parse(currentGoal.endDate)

                if(currentGoal.canCreateNew) {
                    if(storedDate < endDate.plusDays(1)) {
                        _uiEffect.emit(HomeUiEffect.OnGoCurrentGoal(user.await().id))
                    }
                }
                if(storedDate < todayDate) {
                    updateStoredDateUseCase(HomeUiState.getTodayDate())
                }

            } catch (e: Exception) {
            }
        }
    }

    // 얘는 날짜 정보와 월간
    fun onEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.OnRefresh -> {
                onRefresh()
            }

            is HomeUiEvent.OnClickSelectedDate -> {
                onClickSelectedDate(uiEvent.year, uiEvent.month)
            }

            is HomeUiEvent.OnClickFilterType -> {
                onClickFilterType(uiEvent.filterType)
            }

            is HomeUiEvent.OnClickCell -> {
                onClickCell(uiEvent.year, uiEvent.month, uiEvent.day)
            }

            is HomeUiEvent.OnClickAddButton -> {
                onClickAddRecord(uiEvent.recordType)
            }

            is HomeUiEvent.OnClickDetailButton -> {
                onClickDetailRecord(uiEvent.recordType, uiEvent.recordId)
            }

            is HomeUiEvent.OnClickSetting -> {
                onClickSetting()
            }

            is HomeUiEvent.OnClickDeleteItem -> {
                onClickDeleteItem(recordType = uiEvent.recordType, recordId = uiEvent.recordId)
            }

            is HomeUiEvent.OnClickUpdateHabitIsComplete -> {
                onClickHabitRecordIsCompleted(recordId = uiEvent.recordId, isCompleted = uiEvent.isCompleted)
            }
            is HomeUiEvent.OnClickNotification -> {
                onClickNotification()
            }
        }
    }

    private fun onRefresh() {
        viewModelScope.launch {
            if (uiState.value == HomeUiState.init) {
                return@launch
            }
            try {
                val monthlyRecords = async { getMonthlyRecordsUseCase(uiState.value.currentYear, uiState.value.currentMonth, arrayOf()).getOrThrow() }
                val detailDailyRecords = getDailyRecordsUseCase(uiState.value.todayFormat()).getOrThrow()

                val calendarDayInfos = CalendarDayInfo.of(monthlyRecords.await())
                monthlyRecord.update {
                    calendarDayInfos
                }

                val todayRecords = if (detailDailyRecords.date == HomeUiState.getTodayDate()) {
                    detailDailyRecords
                } else {
                    getDailyRecordsUseCase(HomeUiState.getTodayDate()).getOrThrow()
                }

                _uiState.update {
                    it.copy(
                        monthlyRecords = calendarDayInfos,
                        dailyRecordDetails = detailDailyRecords,
                        todayRecords = todayRecords
                    )
                }
            } catch (e: Exception) {

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
                monthlyRecords = monthlyRecord.value.filterMonthlyRecords(filterType.toRecordType() ?: throw IllegalStateException())
            )
        }
    }

    private fun onClickCell(year: Int, month: Int, day: Int) {
        if (uiState.value.currentYear != year || uiState.value.currentMonth != month) {
            onClickSelectedDate(year, month)
        }

        viewModelScope.launch {
            getDailyRecordsUseCase("$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}")
                .onSuccess { dailyRecords ->
                    _uiState.update {
                        it.copy(
                            currentYear = year,
                            currentMonth = month,
                            selectedMonth = month,
                            selectedDay = day,
                            dailyRecordDetails = dailyRecords
                        )
                    }
                }.onFailure {
                }
        }
    }

    private fun onClickAddRecord(recordType: RecordType) {
        viewModelScope.launch {
            if(uiState.value.shouldCreateNewGoal) {
                _uiEffect.emit(HomeUiEffect.OnGoCurrentGoal(uiState.value.userId))
                return@launch
            }
            _uiEffect.emit(HomeUiEffect.OnGoAddRecord(recordType))
        }
    }

    private fun onClickDetailRecord(recordType: RecordType, recordId: String) {
        viewModelScope.launch {
            _uiEffect.emit(HomeUiEffect.OnGoDetailRecord(recordType, recordId))
        }
    }

    private fun onClickSetting() {
        viewModelScope.launch {
            _uiEffect.emit(HomeUiEffect.OnGoSetting)
        }
    }

    private fun onClickDeleteItem(recordType: RecordType, recordId: String) {
        viewModelScope.launch {
            when (recordType) {
                RecordType.DAILY -> {
                    deleteDailyRecordUseCase(recordId)
                        .onSuccess {
                            onRefresh()
                            _toastMessage.emit("기록이 삭제 되었습니다.")
                        }
                }

                RecordType.EXERCISE -> {
                    deleteExerciseRecordUseCase(recordId)
                        .onSuccess {
                            onRefresh()
                            _toastMessage.emit("기록이 삭제 되었습니다.")
                        }
                }

                RecordType.HABIT -> {
                    deleteHabitRecordUseCase(recordId)
                        .onSuccess {
                            onRefresh()
                            _toastMessage.emit("기록이 삭제 되었습니다.")
                        }
                }

//                RecordType.SCHEDULE -> {
//
//                }
            }
        }

    }

    private fun List<CalendarDayInfo>.filterMonthlyRecords(recordType: RecordType): List<CalendarDayInfo> {
        return this.map {
            CalendarDayInfo(
                it.year,
                it.month,
                it.day,
                it.mainRecordType,
                it.records.filter { record ->
                    record == recordType
                },
                it.schedules
            )
        }
    }

    private fun onClickHabitRecordIsCompleted(recordId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            updateHabitRecordIsCompletedUseCase(recordId, isCompleted)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            dailyRecordDetails = it.dailyRecordDetails.copy(
                                records = it.dailyRecordDetails.records.map { record ->
                                    when {
                                        record.id == recordId && record is HabitRecordDetail ->
                                            record.copy(isCompleted = isCompleted)

                                        else -> record
                                    }
                                }
                            )
                        )
                    }
                }
        }
    }

    private fun onClickNotification() {
        viewModelScope.launch {
            _uiEffect.emit(HomeUiEffect.OnGoNotification)
        }
    }
}
