package see.day.goal.screen

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
import see.day.goal.component.ResetGoalTitleDescription
import see.day.goal.component.ResetGoalTopBar
import see.day.goal.state.reset.GoalResetStep
import see.day.goal.state.reset.ResetGoalUiEffect
import see.day.goal.state.reset.ResetGoalUiEvent
import see.day.goal.state.reset.ResetGoalUiState
import see.day.goal.viewModel.ResetGoalViewModel
import see.day.navigation.onboarding.CompleteType
import see.day.ui.screen.GoalsScreen
import see.day.ui.screen.RecordTypeScreen

@Composable
internal fun ResetGoalScreenRoot(
    viewModel: ResetGoalViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onGoResetGoalComplete: (CompleteType) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler(true) {
        viewModel.onAction(ResetGoalUiEvent.OnBack)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                ResetGoalUiEffect.OnBack -> {
                    onBack()
                }
                ResetGoalUiEffect.OnFinishResetGoal -> {
                    onGoResetGoalComplete(CompleteType.RESET_GOAL)
                }
            }
        }
    }

    ResetGoalScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun ResetGoalScreen(
    modifier: Modifier = Modifier,
    uiState: ResetGoalUiState,
    onAction: (ResetGoalUiEvent) -> Unit
) {
    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            ResetGoalTopBar(
                step = uiState.step,
                onClickBackButton = {
                    onAction(ResetGoalUiEvent.OnBack)
                }
            )
        }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .padding(innerPaddings)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            ResetGoalTitleDescription(step = uiState.step)
            Spacer(modifier = Modifier.height(50.dp))
            when(uiState.step) {
                GoalResetStep.RECORD -> {
                    RecordTypeScreen(
                        selectedRecordType = uiState.recordType,
                        onClickCompleteButton = { selectedRecordType ->
                            onAction(ResetGoalUiEvent.SetRecordType(selectedRecordType))
                        }
                    )
                }
                GoalResetStep.DAY -> {
                    GoalsScreen(
                        goalDays = uiState.goalDays,
                        completeButtonText = "완료하기",
                        onComplete = { newGoalDays ->
                            onAction(ResetGoalUiEvent.SetGoalDays(newGoalDays))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ResetGoalScreenPreview() {
    SeeDayTheme {
        ResetGoalScreen(
            uiState = ResetGoalUiState.init,
            onAction = {}
        )
    }
}
