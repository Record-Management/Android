package see.day.habit.viewModel

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
import see.day.domain.usecase.record.habit.CanSetAsMainRecordUseCase
import see.day.domain.usecase.record.habit.DeleteHabitRecordUseCase
import see.day.domain.usecase.record.habit.GetHabitRecordUseCase
import see.day.domain.usecase.record.habit.InsertHabitRecordUseCase
import see.day.domain.usecase.record.habit.UpdateHabitRecordUseCase
import see.day.habit.state.HabitDetailUiEffect
import see.day.habit.state.HabitDetailUiEvent
import see.day.habit.state.HabitDetailUiState
import see.day.habit.state.HabitRecordPostType
import see.day.model.record.habit.HabitRecordEdit
import see.day.model.record.habit.HabitRecordInput
import see.day.model.record.habit.HabitType
import javax.inject.Inject

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    private val insertHabitRecordUseCase: InsertHabitRecordUseCase,
    private val updateHabitRecordUseCase: UpdateHabitRecordUseCase,
    private val deleteHabitRecordUseCase: DeleteHabitRecordUseCase,
    private val getHabitRecordUseCase: GetHabitRecordUseCase,
    private val canSetAsMainRecordUseCase: CanSetAsMainRecordUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HabitDetailUiState> = MutableStateFlow(HabitDetailUiState.init)
    val uiState: StateFlow<HabitDetailUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<HabitDetailUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<HabitDetailUiEffect> = _uiEffect.asSharedFlow()

    private val _toastMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    fun fetchData(type: HabitRecordPostType) {
        viewModelScope.launch {
            when (type) {
                is HabitRecordPostType.Write -> {
                    canSetAsMainRecordUseCase(uiState.value.recordDate).onSuccess { canSetAsMainRecord ->
                        _uiState.update {
                            it.copy(
                                habitType = type.habitType,
                                canBeMain = canSetAsMainRecord
                            )
                        }
                    }
                }

                is HabitRecordPostType.Edit -> {
                    getHabitRecordUseCase(type.id).onSuccess { habitRecord ->
                        _uiState.update {
                            it.copy(
                                habitType = habitRecord.habitType,
                                notificationEnabled = habitRecord.notificationEnabled,
                                hour = habitRecord.notificationHour,
                                minute = habitRecord.notificationMinute,
                                memo = habitRecord.memo,
                                editMode = HabitDetailUiState.EditMode.Edit(
                                    recordId = habitRecord.id,
                                    originalRecord = habitRecord
                                )
                            )
                        }
                    }
                }
            }
        }

    }

    fun onEvent(uiEvent: HabitDetailUiEvent) {
        when (uiEvent) {
            is HabitDetailUiEvent.OnHabitTypeChanged -> {
                onHabitTypeChanged(uiEvent.habitType)
            }

            is HabitDetailUiEvent.OnNotificationEnabledChanged -> {
                onNotificationEnabledChanged(uiEvent.enabled)
            }

            is HabitDetailUiEvent.OnAlertTimeChanged -> {
                onAlertTimeChanged(uiEvent.hour, uiEvent.minute)
            }

            is HabitDetailUiEvent.OnMemoChanged -> {
                onMemoChanged(uiEvent.memo)
            }

            HabitDetailUiEvent.OnSaveRecord -> {
                onSaveRecord()
            }

            is HabitDetailUiEvent.DeleteRecord -> {
                deleteRecord(uiEvent.recordId)
            }

            is HabitDetailUiEvent.OnTimeSpinnerDisplay -> {
                _uiState.update {
                    it.copy(isTimeSpinnerDisplayed = uiEvent.displayed)
                }
            }
            is HabitDetailUiEvent.OnSetAsMainHabit -> {
                _uiState.update {
                    it.copy(hasBeenSetAsMain = uiEvent.changed)
                }
            }
        }
    }

    private fun onHabitTypeChanged(habitType: HabitType) {
        _uiState.update {
            it.copy(
                habitType = habitType
            )
        }
    }

    private fun onNotificationEnabledChanged(enabled: Boolean) {
        _uiState.update {
            it.copy(
                notificationEnabled = enabled,
                isTimeSpinnerDisplayed = enabled
            )
        }
    }

    private fun onAlertTimeChanged(hour: Int, minute: Int) {
        _uiState.update {
            it.copy(
                hour = hour,
                minute = minute
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

    private fun onSaveRecord() {
        viewModelScope.launch {
            when (val mode = uiState.value.editMode) {
                is HabitDetailUiState.EditMode.Create -> {
                    saveHabitRecordForCreateMode()
                }

                is HabitDetailUiState.EditMode.Edit -> {
                    updateHabitRecord(mode.recordId)
                }
            }
        }
    }

    private suspend fun saveHabitRecordForCreateMode() {
        insertHabitRecordUseCase(
            HabitRecordInput(
                habitType = uiState.value.habitType,
                notificationEnabled = uiState.value.notificationEnabled,
                notificationHour = uiState.value.hour,
                notificationMinute = uiState.value.minute,
                memo = uiState.value.memo,
                recordDate = uiState.value.recordDate
            )
        ).onSuccess {
            _uiEffect.emit(HabitDetailUiEffect.OnPopHome(true))
        }.onFailure {

        }
    }

    private suspend fun updateHabitRecord(recordId: String) {
        updateHabitRecordUseCase(
            recordId = recordId,
            habitRecordEdit = HabitRecordEdit(
                habitType = uiState.value.habitType,
                notificationEnabled = uiState.value.notificationEnabled,
                hour = uiState.value.hour,
                minute = uiState.value.minute,
                memo = uiState.value.memo,
            )
        ).onSuccess {
            _uiEffect.emit(HabitDetailUiEffect.OnPopHome(true))
        }
    }

    private fun deleteRecord(recordId: String) {
        viewModelScope.launch {
            deleteHabitRecordUseCase(recordId)
                .onSuccess {
                    _uiEffect.emit(HabitDetailUiEffect.OnPopHome(isUpdated = true))
                    _toastMessage.emit("기록이 삭제 되었습니다.")
                }
        }
    }

}
