package see.day.onboarding.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.navigation.onboarding.CompleteType
import see.day.onboarding.component.OnboardingTopBar
import see.day.onboarding.component.TitleDescription
import see.day.onboarding.screen.onboarding.AlertScreen
import see.day.onboarding.screen.onboarding.BirthdayScreen
import see.day.onboarding.screen.onboarding.NicknameScreen
import see.day.onboarding.screen.onboarding.TermsScreen
import see.day.onboarding.state.OnboardingScreenState.ALERT
import see.day.onboarding.state.OnboardingScreenState.BIRTHDAY
import see.day.onboarding.state.OnboardingScreenState.GOAL
import see.day.onboarding.state.OnboardingScreenState.NICKNAME
import see.day.onboarding.state.OnboardingScreenState.RECORD
import see.day.onboarding.state.OnboardingScreenState.TERMS
import see.day.onboarding.state.onboarding.OnboardingUiEffect
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.onboarding.state.onboarding.OnboardingUiState
import see.day.onboarding.viewModel.OnboardingViewModel
import see.day.ui.screen.GoalsScreen
import see.day.ui.screen.RecordTypeScreen

@Composable
internal fun OnboardingScreenRoot(viewModel: OnboardingViewModel = hiltViewModel(), onBack: () -> Unit, onGoOnboardingComplete: (CompleteType) -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    BackHandler(true) {
        viewModel.onAction(OnboardingUiEvent.OnClickBack)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                OnboardingUiEffect.FinishApp -> {
                    onBack()
                }

                OnboardingUiEffect.NavigateToOnboardingComplete -> {
                    onGoOnboardingComplete(CompleteType.ONBOARDING)
                }
            }
        }
    }

    OnboardingScreen(
        uiState = uiState,
        uiEvent = viewModel::onAction
    )
}

@Composable
internal fun OnboardingScreen(uiState: OnboardingUiState, uiEvent: (OnboardingUiEvent) -> Unit, modifier: Modifier = Modifier) {
    if(uiState.onboardingScreenState == TERMS) {
        TermsScreen(
            onClick = {uiEvent(OnboardingUiEvent.ConfirmTerms)}
        )
        return
    }
    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            OnboardingTopBar(
                modifier,
                uiState,
                uiEvent
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp).fillMaxSize(),
        ) {
            TitleDescription(modifier, uiState)
            Spacer(modifier = modifier.height(50.dp))
            when (uiState.onboardingScreenState) {
                RECORD -> {
                    RecordTypeScreen (
                        selectedRecordType = uiState.mainRecordType,
                        onClickCompleteButton = { recordType ->
                            uiEvent(OnboardingUiEvent.SetRecordType(recordType))
                        }
                    )
                }

                NICKNAME -> {
                    NicknameScreen(
                        nickname = uiState.nickname,
                        onComplete = uiEvent
                    )
                }

                BIRTHDAY -> {
                    BirthdayScreen(
                        birthDay = uiState.birthDate,
                        onClickComplete = uiEvent
                    )
                }
                GOAL -> {
                    GoalsScreen(
                        goalDays = uiState.goalDays,
                        onComplete = { goalDays ->
                            uiEvent(OnboardingUiEvent.SetGoalDays(goalDays))
                        }
                    )
                }
                ALERT -> {
                    AlertScreen(
                        onClickComplete = uiEvent
                    )
                }
                TERMS -> {}
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    SeeDayTheme {
        OnboardingScreen(
            uiState = OnboardingUiState.init.copy(onboardingScreenState = BIRTHDAY),
            uiEvent = {}
        )
    }
}

@Preview
@Composable
private fun OnboardingScreenInitPreview() {
    SeeDayTheme {
        OnboardingScreen(
            uiState = OnboardingUiState.init,
            uiEvent = {}
        )
    }
}
