package see.day.goal.viewModel

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
import see.day.analytics.AnalyticsLogger
import see.day.analytics.types.GoalSettingType
import see.day.domain.usecase.goal.PostNewGoalUseCase
import see.day.goal.state.reset.GoalResetStep.DAY
import see.day.goal.state.reset.GoalResetStep.RECORD
import see.day.goal.state.reset.ResetGoalUiEffect
import see.day.goal.state.reset.ResetGoalUiEvent
import see.day.goal.state.reset.ResetGoalUiState
import see.day.model.goal.NewGoal
import see.day.model.record.RecordType
import javax.inject.Inject

@HiltViewModel
class ResetGoalViewModel @Inject constructor(
    private val postNewGoalUseCase: PostNewGoalUseCase,
    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    private val _uiState: MutableStateFlow<ResetGoalUiState> = MutableStateFlow(ResetGoalUiState.init)
    val uiState: StateFlow<ResetGoalUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<ResetGoalUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<ResetGoalUiEffect> = _uiEffect.asSharedFlow()

    fun onAction(event: ResetGoalUiEvent) {
        when (event) {
            is ResetGoalUiEvent.SetRecordType -> {
                setRecordType(event.recordType)
            }

            is ResetGoalUiEvent.SetGoalDays -> {
                setGoalDays(event.goalDays)
            }

            ResetGoalUiEvent.OnClickBack -> {
                onBack()
            }
        }
    }

    private fun setRecordType(recordType: RecordType) {
        _uiState.update {
            it.copy(
                step = DAY,
                recordType = recordType
            )
        }
    }

    private fun setGoalDays(goalDays: Int) {
        viewModelScope.launch {
            uiState.value.recordType?.let { recordType ->
                postNewGoalUseCase(NewGoal(recordType, goalDays))
                    .onSuccess {
                        analyticsLogger.goalSettingLog(GoalSettingType.GOAL_RESET, recordType.name.lowercase(), goalDays)
                        _uiEffect.emit(ResetGoalUiEffect.NavigateToFinishResetGoal)
                    }
            }
        }
    }

    private fun onBack() {
        val step = uiState.value.step
        if (step == DAY) {
            _uiState.update {
                it.copy(
                    step = RECORD,
                )
            }
        } else {
            viewModelScope.launch {
                _uiEffect.emit(ResetGoalUiEffect.NavigateToBack)
            }
        }
    }
}
