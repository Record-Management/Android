package see.day.goal.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray60
import see.day.designsystem.theme.primaryColor
import see.day.goal.R

@Composable
internal fun GoalAchievementComponent(
    modifier: Modifier = Modifier,
    achievementRate: Int,
    completedDays: Int,
    dayStreak: Int
) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AchievementPercent(achievementRate)
        CompleteDays(completedDays)
        DayStreak(dayStreak)
    }
}

@Composable
private fun AchievementPercent(achievementRate: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_achievement_rate),
            contentDescription = "목표달성률",
            modifier = Modifier.size(50.dp)
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "${achievementRate}%",
            style = MaterialTheme.typography.bodyLarge.copy(color = primaryColor)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.achievement_rate_text),
            style = MaterialTheme.typography.headlineLarge.copy(gray60),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun CompleteDays(completedDays: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_completed_day),
            contentDescription = "기록 완료일 수",
            modifier = Modifier.size(50.dp)
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "${completedDays}일",
            style = MaterialTheme.typography.bodyLarge.copy(color = primaryColor)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.complete_days_text),
            style = MaterialTheme.typography.headlineLarge.copy(gray60),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DayStreak(dayStreak: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_day_streak),
            contentDescription = "누적 달성 횟수",
            modifier = Modifier.size(50.dp)
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "${dayStreak}회",
            style = MaterialTheme.typography.bodyLarge.copy(color = primaryColor)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.day_streak_text),
            style = MaterialTheme.typography.headlineLarge.copy(gray60),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun GoalAchievementComponentPreview() {
    SeeDayTheme {
        GoalAchievementComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp),
            achievementRate = 80,
            completedDays = 8,
            dayStreak = 0
        )
    }
}
