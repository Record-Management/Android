package see.day.onboarding.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import see.day.model.record.RecordType
import see.day.onboarding.state.OnboardingScreenState
import see.day.onboarding.state.OnboardingScreenState.ALERT
import see.day.onboarding.state.OnboardingScreenState.BIRTHDAY
import see.day.onboarding.state.OnboardingScreenState.GOAL
import see.day.onboarding.state.OnboardingScreenState.NICKNAME
import see.day.onboarding.state.OnboardingScreenState.RECORD
import see.day.onboarding.state.onboarding.OnboardingUiEffect
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.onboarding.state.onboarding.OnboardingUiState

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<OnboardingUiState> = MutableStateFlow(OnboardingUiState.init)
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<OnboardingUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<OnboardingUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: OnboardingUiEvent) {
        when (event) {
            is OnboardingUiEvent.SetRecordType -> {
                setRecordType(event.recordType)
            }

            is OnboardingUiEvent.EnterNickname -> {
                setNickname(event.name)
            }

            is OnboardingUiEvent.EnterBirthDay -> {
                setBirthday(event.birthDay)
            }

            is OnboardingUiEvent.EnterGoal -> {
                setGoalDay(event.goalDay)
            }

            is OnboardingUiEvent.CheckNotification -> {
                setNotificationEnabled(event.notificationEnabled)
            }

            OnboardingUiEvent.OnBack -> {
                onBack()
            }
        }
    }

    private fun setRecordType(recordType: RecordType) {
        _uiState.update {
            it.copy(
                onboardingScreenState = NICKNAME,
                mainRecordType = recordType
            )
        }
    }

    private fun setNickname(nickname: String) {
        _uiState.update {
            it.copy(
                onboardingScreenState = BIRTHDAY,
                nickname = nickname
            )
        }
    }

    private fun setBirthday(birthday: String) {
        _uiState.update {
            it.copy(
                onboardingScreenState = GOAL,
                birthDate = birthday
            )
        }
    }

    private fun setGoalDay(goalDay: Int) {
        _uiState.update {
            it.copy(
                onboardingScreenState = ALERT,
                goalDays = goalDay
            )
        }
    }

    private fun setNotificationEnabled(enabled: Boolean) {
        _uiState.update {
            it.copy(
                notificationEnabled = enabled
            )
        }
        // UiEffect를 GoOnboardFinish로 이동
    }

    private fun onBack() {
        val currentScreenState = uiState.value.onboardingScreenState
        if (currentScreenState == RECORD) {
            viewModelScope.launch {
                _uiEffect.emit(OnboardingUiEffect.FinishApp)
            }
        } else {
            _uiState.update {
                it.copy(
                    onboardingScreenState = OnboardingScreenState.entries[it.onboardingScreenState.ordinal - 1]
                )
            }
        }
    }
}
