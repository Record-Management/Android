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
import see.day.domain.usecase.goal.GetRecentGoalReportUseCase
import see.day.goal.state.CurrentGoalUiEffect
import see.day.goal.state.CurrentGoalUiEvent
import see.day.goal.state.CurrentGoalUiState
import javax.inject.Inject

@HiltViewModel
class CurrentGoalViewModel @Inject constructor(
    private val getRecentGoalReportUseCase: GetRecentGoalReportUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CurrentGoalUiState> = MutableStateFlow(CurrentGoalUiState.init)
    val uiState: StateFlow<CurrentGoalUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<CurrentGoalUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<CurrentGoalUiEffect> = _uiEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            getRecentGoalReportUseCase().onSuccess { recentGoalReport ->
                _uiState.update {
                    it.copy(
                        treeStage = recentGoalReport.treeStage,
                        recordType = recentGoalReport.recordType,
                        startDate = recentGoalReport.startDate,
                        endDate = recentGoalReport.endDate,
                        achievementRate = recentGoalReport.achievementRate.toInt(),
                        completedDays = recentGoalReport.completedDays,
                        dayStreak = recentGoalReport.cumulativeAchievementCount,
                        isCompleted = recentGoalReport.isGoalCompleted
                    )
                }
            }
        }
    }

    fun onAction(uiEvent: CurrentGoalUiEvent) {
        when(uiEvent) {
            CurrentGoalUiEvent.OnClickBack -> {
                onClickBack()
            }
            CurrentGoalUiEvent.OnClickGoalBanner -> {
                onClickGoalBanner()
            }
        }
    }

    private fun onClickBack() {
        viewModelScope.launch {
            _uiEffect.emit(CurrentGoalUiEffect.NavigateToBack)
        }
    }

    private fun onClickGoalBanner() {
        viewModelScope.launch {
            _uiEffect.emit(CurrentGoalUiEffect.NavigateToResetGoalSetting)
        }
    }
}
