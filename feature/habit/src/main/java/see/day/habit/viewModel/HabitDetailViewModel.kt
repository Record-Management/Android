package see.day.habit.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import see.day.habit.state.HabitDetailUiEffect
import see.day.habit.state.HabitDetailUiEvent
import see.day.habit.state.HabitDetailUiState
import see.day.habit.state.HabitRecordPostType
import see.day.model.record.habit.HabitType
import javax.inject.Inject

@HiltViewModel
class HabitDetailViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState: MutableStateFlow<HabitDetailUiState> = MutableStateFlow(HabitDetailUiState.init)
    val uiState: StateFlow<HabitDetailUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<HabitDetailUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<HabitDetailUiEffect> = _uiEffect.asSharedFlow()

    fun fetchData(type: HabitRecordPostType) {
        when(type) {
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

}
