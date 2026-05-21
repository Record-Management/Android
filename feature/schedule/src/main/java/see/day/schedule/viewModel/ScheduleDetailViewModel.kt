package see.day.schedule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import see.day.domain.repository.ScheduleRepository
import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.model.schedule.ScheduleInput
import see.day.model.schedule.SchedulePaletteColor
import see.day.schedule.state.ScheduleDetailUiEffect
import see.day.schedule.state.ScheduleDetailUiEvent
import see.day.schedule.state.ScheduleDetailUiState
import see.day.schedule.state.SchedulePostType
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ScheduleDetailUiState> = MutableStateFlow(ScheduleDetailUiState.init)
    val uiState: StateFlow<ScheduleDetailUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<ScheduleDetailUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<ScheduleDetailUiEffect> = _uiEffect.asSharedFlow()

    fun fetchData(type: SchedulePostType) {
        viewModelScope.launch {
            when (type) {
                is SchedulePostType.Write -> {
                }

                is SchedulePostType.Edit -> {
                    scheduleRepository.getSchedule(type.id).onSuccess { scheduleDetail ->
                        _uiState.update {
                            it.copy(
                                title = scheduleDetail.title,
                                startDate = scheduleDetail.startDate,
                                endDate = scheduleDetail.endDate,
                                alertType = scheduleDetail.alertType,
                                notificationCustomHours = scheduleDetail.notificationCustomHours,
                                notificationCustomMinutes = scheduleDetail.notificationCustomMinutes,
                                repeatType = scheduleDetail.repeatType,
                                repeatEndsOn = scheduleDetail.repeatEndsOn,
                                location = it.location,
                                color = it.color,
                                memo = it.memo
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAction(uiEvent: ScheduleDetailUiEvent) {
        when (uiEvent) {
            is ScheduleDetailUiEvent.OnTitleChanged -> {
                onTitleChanged(uiEvent.title)
            }
            is ScheduleDetailUiEvent.OnStartDateChanged -> {
                onStartDateChanged(uiEvent.startDate)
            }
            is ScheduleDetailUiEvent.OnEndDateChanged -> {
                onEndDateChanged(uiEvent.endDate)
            }
            is ScheduleDetailUiEvent.OnAlertTimeChanged -> {
                onAlertTimeChanged(uiEvent.alertTime, uiEvent.hour, uiEvent.minute)
            }
            is ScheduleDetailUiEvent.OnRepeatTypeChanged -> {
                onRepeatTimeChanged(uiEvent.repeatTime, uiEvent.repeatEndTime)
            }
            is ScheduleDetailUiEvent.OnLocationChanged -> {
                onLocationChanged(uiEvent.location)
            }
            is ScheduleDetailUiEvent.OnColorChanged -> {
                onColorChanged(uiEvent.color)
            }
            is ScheduleDetailUiEvent.OnMemoChanged -> {
                onMemoChanged(uiEvent.memo)
            }
            is ScheduleDetailUiEvent.OnSaveSchedule -> {
                onSaveSchedule()
            }
        }
    }

    private fun onTitleChanged(title: String) {
        _uiState.update {
            it.copy(
                title = title
            )
        }
    }

    private fun onStartDateChanged(startDate: LocalDate) {
        _uiState.update {
            it.copy(
                startDate = startDate
            )
        }
    }

    private fun onEndDateChanged(endDate: LocalDate) {
        _uiState.update {
            it.copy(
                endDate = endDate
            )
        }
    }

    private fun onAlertTimeChanged(alertTime: AlertTime, notificationCustomHours: Int, notificationCustomMinutes: Int) {
        _uiState.update {
            it.copy(
                alertType = alertTime,
                notificationCustomHours = notificationCustomHours,
                notificationCustomMinutes = notificationCustomMinutes
            )
        }
    }

    private fun onRepeatTimeChanged(repeatTime: RepeatTime, repeatEndsTime: LocalDate?) {
        _uiState.update {
            it.copy(
                repeatType = repeatTime,
                repeatEndsOn = repeatEndsTime
            )
        }
    }

    private fun onLocationChanged(location: String) {
        _uiState.update {
            it.copy(
                location = location
            )
        }
    }

    private fun onColorChanged(color: SchedulePaletteColor) {
        _uiState.update {
            it.copy(
                color = color
            )
        }
    }

    private fun onMemoChanged(memo: String) {
        _uiState.update {
            it.copy(
                memo = memo
            )
        }
    }

    private fun onSaveSchedule() {
        viewModelScope.launch {
            when(val mode = uiState.value.editMode) {
                is ScheduleDetailUiState.EditMode.Create -> {
                    saveScheduleForCreateMode()
                }
                is ScheduleDetailUiState.EditMode.Edit -> {

                }
            }
        }
    }

    private suspend fun saveScheduleForCreateMode() {
        scheduleRepository.insertSchedule(
            ScheduleInput(
                title = uiState.value.title,
                startDate = uiState.value.startDate,
                endDate = uiState.value.endDate,
                notificationType = uiState.value.alertType,
                notificationCustomHours = uiState.value.notificationCustomHours,
                notificationCustomMinutes = uiState.value.notificationCustomMinutes,
                repeatType = uiState.value.repeatType,
                repeatEndsOn = uiState.value.repeatEndsOn,
                location = uiState.value.location,
                color = uiState.value.color,
                memo = uiState.value.memo
            )
        )
    }
}
