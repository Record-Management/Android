package see.day.goal.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.goal.R
import see.day.goal.component.GoalAchievementComponent
import see.day.goal.component.GoalRecordDateCard
import see.day.goal.state.CurrentGoalUiState
import see.day.ui.card.ActionBanner
import see.day.ui.topbar.ClosableTopBar

@Composable
internal fun CurrentGoalScreenRoot(
    onBack : () -> Unit
) {
    val uiState by remember { mutableStateOf(CurrentGoalUiState.init) }
    CurrentGoalScreen(
        onBack = onBack,
        uiState = uiState
    )
}

@Composable
internal fun CurrentGoalScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    uiState: CurrentGoalUiState
) {
    Scaffold(
        topBar = {
            ClosableTopBar(
                modifier = modifier.padding(horizontal = 16.dp),
                onClose = onBack,
                title = R.string.goal_acheive_title
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color(0xFFFFFFFF),
                            0.55f to Color(0xFFFFFFFF),
                            1.0f to Color(0xFFFFE0C1)
                        )
                    )
                )
                .padding(horizontal = 26.dp)
        ) {
            GoalRecordDateCard(
                modifier = Modifier.fillMaxWidth().padding(top = 26.dp),
                recordType = uiState.recordType,
                startDate = uiState.startDate,
                endDate = uiState.endDate
            )
            GoalAchievementComponent(
                modifier = Modifier.fillMaxWidth().padding(top = 34.dp),
                achievementRate = uiState.achievementRate,
                completedDays = uiState.completedDays,
                dayStreak = uiState.dayStreak
            )
            ActionBanner(
                modifier = Modifier.fillMaxWidth().padding(top = 34.dp, bottom = 52.dp).systemBarsPadding(),
                onClick = {},
                title = R.string.current_goal_banner_title,
                body = R.string.current_goal_banner_body
            )
        }
    }
}

@Preview
@Composable
private fun CurrentGoalScreenPreview() {
    SeeDayTheme {
        CurrentGoalScreen(
            onBack = {},
            uiState = CurrentGoalUiState.init
        )
    }
}
