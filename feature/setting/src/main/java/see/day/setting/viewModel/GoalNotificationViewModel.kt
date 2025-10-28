package see.day.setting.viewModel

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
import see.day.setting.state.goal.GoalNotificationUiEffect
import see.day.setting.state.goal.GoalNotificationUiEvent
import see.day.setting.state.goal.GoalNotificationUiState
import javax.inject.Inject

@HiltViewModel
class GoalNotificationViewModel @Inject constructor() :ViewModel() {

    private val _uiState : MutableStateFlow<GoalNotificationUiState> = MutableStateFlow(GoalNotificationUiState.init)
    val uiState : StateFlow<GoalNotificationUiState> = _uiState.asStateFlow()

    private val _uiEffect : MutableSharedFlow<GoalNotificationUiEffect> = MutableSharedFlow()
    val uiEffect : SharedFlow<GoalNotificationUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(uiEvent: GoalNotificationUiEvent) {
        when(uiEvent) {
            is GoalNotificationUiEvent.OnChangedGoalNotification -> {
                onChangedGoalNotification(enabled = uiEvent.enabled)
            }

            GoalNotificationUiEvent.OnGoBack -> {
                onGoBack()
            }
        }
    }

    private fun onChangedGoalNotification(enabled : Boolean) {
        _uiState.update {
            it.copy(
                goalNotificationEnabled = enabled
            )
        }
    }

    private fun onGoBack() {
        viewModelScope.launch {
            _uiEffect.emit(GoalNotificationUiEffect.OnGoBack)
        }
    }
}
