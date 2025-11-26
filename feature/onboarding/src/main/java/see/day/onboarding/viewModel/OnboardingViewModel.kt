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
import see.day.domain.usecase.fcm.GetFcmTokenUseCase
import see.day.domain.usecase.user.PostOnboardCompleteUseCase
import see.day.domain.usecase.user.UpdateFcmTokenUseCase
import see.day.model.record.RecordType
import see.day.model.user.OnboardingComplete
import see.day.onboarding.state.OnboardingScreenState
import see.day.onboarding.state.OnboardingScreenState.ALERT
import see.day.onboarding.state.OnboardingScreenState.BIRTHDAY
import see.day.onboarding.state.OnboardingScreenState.GOAL
import see.day.onboarding.state.OnboardingScreenState.NICKNAME
import see.day.onboarding.state.OnboardingScreenState.RECORD
import see.day.onboarding.state.onboarding.OnboardingUiEffect
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.onboarding.state.onboarding.OnboardingUiState
import timber.log.Timber

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val postOnboardCompleteUseCase: PostOnboardCompleteUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<OnboardingUiState> = MutableStateFlow(OnboardingUiState.init)
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<OnboardingUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<OnboardingUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: OnboardingUiEvent) {
        when (event) {
            is OnboardingUiEvent.ConformTerms -> {
                confirmTerms()
            }
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
            is OnboardingUiEvent.FinishOnboarding -> {
                finishOnboarding()
            }
            OnboardingUiEvent.OnBack -> {
                onBack()
            }
        }
    }

    private fun confirmTerms() {
        _uiState.update {
            it.copy(
                onboardingScreenState = RECORD
            )
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

    private fun finishOnboarding() {
        val onboardState = uiState.value
        val onboardingComplete = OnboardingComplete(
            nickname = onboardState.nickname,
            mainRecordType = onboardState.mainRecordType ?: RecordType.DAILY,
            birthDate = onboardState.birthDate,
            goalDays = onboardState.goalDays
        )
        viewModelScope.launch {
            postOnboardCompleteUseCase(onboardingComplete)
                .onSuccess {
                    getFcmTokenUseCase()
                        .onSuccess { token ->
                            updateFcmTokenUseCase(token).onSuccess {
                                _uiEffect.emit(OnboardingUiEffect.GoOnboardingComplete)
                            }
                        }
                }.onFailure {
                    Timber.e(it)
                }
        }
    }

    private fun onBack() {
        val currentScreenState = uiState.value.onboardingScreenState
        if (currentScreenState == OnboardingScreenState.entries[0]) {
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
