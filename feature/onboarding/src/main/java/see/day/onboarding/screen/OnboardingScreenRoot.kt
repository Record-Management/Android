package see.day.onboarding.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import see.day.onboarding.component.OnboardingTopBar
import see.day.onboarding.component.TitleDescription
import see.day.onboarding.screen.onboarding.RecordTypeScreen
import see.day.onboarding.state.OnboardingScreenState
import see.day.onboarding.state.OnboardingScreenState.RECORD
import see.day.onboarding.state.onboarding.OnboardingUiEffect
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.onboarding.state.onboarding.OnboardingUiState
import see.day.onboarding.viewModel.OnboardingViewModel

@Composable
internal fun OnboardingScreenRoot(viewModel: OnboardingViewModel = hiltViewModel(), onBack: () -> Unit, onGoHome: () -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    BackHandler(true) {
        viewModel.onEvent(OnboardingUiEvent.OnBack)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                OnboardingUiEffect.FinishApp -> {
                    onBack()
                }

                OnboardingUiEffect.GoHome -> {
                    onGoHome()
                }
            }
        }
    }

    OnboardingScreen(
        uiState = uiState,
        uiEvent = viewModel::onEvent
    )
}

@Composable
internal fun OnboardingScreen(uiState: OnboardingUiState, uiEvent: (OnboardingUiEvent) -> Unit, modifier: Modifier = Modifier) {
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
            modifier = Modifier.padding(innerPadding)
        ) {
            TitleDescription(modifier, uiState)
            Spacer(modifier = modifier.height(34.dp))
            when (uiState.onboardingScreenState) {
                RECORD -> {
                    RecordTypeScreen(
                        selectedRecordType = uiState.mainRecordType,
                        onClickCompleteButton = uiEvent
                    )
                }
                OnboardingScreenState.NICKNAME -> TODO()
                OnboardingScreenState.BIRTHDAY -> TODO()
                OnboardingScreenState.GOAL -> TODO()
                OnboardingScreenState.ALERT -> TODO()
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    SeeDayTheme {
        OnboardingScreen(
            uiState = OnboardingUiState.init.copy(onboardingScreenState = RECORD),
            uiEvent = {}
        )
    }
}
