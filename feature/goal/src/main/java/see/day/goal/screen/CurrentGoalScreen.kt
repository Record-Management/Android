package see.day.goal.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collect
import see.day.designsystem.theme.SeeDayTheme
import see.day.goal.R
import see.day.goal.component.GoalAchievementComponent
import see.day.goal.component.GoalRecordDateCard
import see.day.goal.state.CurrentGoalUiEffect
import see.day.goal.state.CurrentGoalUiEvent
import see.day.goal.state.CurrentGoalUiState
import see.day.goal.util.completeText
import see.day.goal.util.treeImage
import see.day.goal.viewModel.CurrentGoalViewModel
import see.day.model.goal.TreeStage
import see.day.ui.card.ActionBanner
import see.day.ui.topbar.ClosableTopBar

@Composable
internal fun CurrentGoalScreenRoot(
    viewModel: CurrentGoalViewModel = hiltViewModel(),
    onBack : () -> Unit,
    onClickResetGoal: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                CurrentGoalUiEffect.OnGoBack -> {
                    onBack()
                }
                CurrentGoalUiEffect.OnGoGoalSetting -> {
                    onClickResetGoal()
                }
            }
        }
    }

    CurrentGoalScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun CurrentGoalScreen(
    modifier: Modifier = Modifier,
    uiState: CurrentGoalUiState,
    onAction: (CurrentGoalUiEvent) -> Unit
) {
    Scaffold(
        modifier =  modifier.background(Color(0xFFF2FCF3))
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color(0xFFF2FCF3))
        ) {
            Box(
                modifier = Modifier.weight(0.4f).fillMaxWidth()
            ) {
                Column(
                    modifier=  Modifier.fillMaxWidth().align(Alignment.TopCenter)
                ) {
                    ClosableTopBar(
                        modifier = Modifier.background(Color(0xFFF2FCF3)).padding(16.dp),
                        onClose = {
                            onAction(CurrentGoalUiEvent.OnClickBack)
                                  },
                        title = R.string.goal_acheive_title
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        text = stringResource(uiState.treeStage.completeText()),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Image(
                    painter = painterResource(uiState.treeStage.treeImage()),
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    contentDescription = uiState.treeStage.name
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to Color(0xFFFFFFFF),
                                0.65f to Color(0xFFFFFFFF),
                                1.0f to Color(0xFFFFE0C1)
                            )
                        )
                    ).padding(horizontal = 26.dp)
            ) {
                GoalRecordDateCard(
                    modifier = Modifier.fillMaxWidth().padding(top = 26.dp),
                    recordType = uiState.recordType,
                    startDate = uiState.startDate,
                    endDate = uiState.endDate
                )
                GoalAchievementComponent(
                    modifier = Modifier.fillMaxWidth().padding(top = 41.dp),
                    achievementRate = uiState.achievementRate,
                    completedDays = uiState.completedDays,
                    dayStreak = uiState.dayStreak
                )
                if(uiState.isCompleted) {
                    Spacer(modifier = Modifier.weight(1f))
                    ActionBanner(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp).navigationBarsPadding(),
                        onClick = { onAction(CurrentGoalUiEvent.OnClickGoalBanner)},
                        title = see.day.ui.R.string.current_goal_banner_title,
                        body = see.day.ui.R.string.current_goal_banner_body
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun CurrentGoalScreenPreview() {
    SeeDayTheme {
        CurrentGoalScreen(
            uiState = CurrentGoalUiState.init,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun CurrentGoalScreenTreeStage4Preview() {
    SeeDayTheme {
        CurrentGoalScreen(
            uiState = CurrentGoalUiState.init.copy(treeStage = TreeStage.STAGE_4),
            onAction = {}
        )
    }
}
