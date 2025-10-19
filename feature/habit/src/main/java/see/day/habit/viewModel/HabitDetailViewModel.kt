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
import see.day.domain.usecase.record.habit.InsertHabitRecordUseCase
import see.day.habit.state.HabitDetailUiEffect
import see.day.habit.state.HabitDetailUiEvent
import see.day.habit.state.HabitDetailUiState
import see.day.habit.state.HabitRecordPostType
import see.day.model.record.habit.HabitRecordInput
import see.day.model.record.habit.HabitType
import javax.inject.Inject

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    private val insertHabitRecordUseCase: InsertHabitRecordUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HabitDetailUiState> = MutableStateFlow(HabitDetailUiState.init)
    val uiState: StateFlow<HabitDetailUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<HabitDetailUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<HabitDetailUiEffect> = _uiEffect.asSharedFlow()

    fun fetchData(type: HabitRecordPostType) {
        when (type) {
            is HabitRecordPostType.Write -> {
                _uiState.update {
                    it.copy(
                        habitType = type.habitType
                    )
                }
            }

            is HabitRecordPostType.Edit -> {

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
                notificationEnabled = enabled
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

}
