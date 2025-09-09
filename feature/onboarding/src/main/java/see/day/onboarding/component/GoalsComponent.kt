package see.day.onboarding.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat.Surface
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.onboarding.R

@Composable
internal fun GoalsComponent(modifier: Modifier = Modifier, goals: Int, currentGoals: Int, onClick: (Int) -> Unit) {
    Surface(
        modifier = modifier
            .height(157.dp)
            .width(104.dp)
            .clickable { onClick(goals) },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (goals == currentGoals) MaterialTheme.colorScheme.primary else gray20)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (goals == 10) {
                Image(
                    painter = painterResource(R.drawable.goal_10),
                    contentDescription = "goal 10 image",
                    modifier = modifier.size(56.dp)
                )
                Text(
                    modifier = modifier
                        .padding(top = 14.dp)
                        .background(Color(0xFFFFF9E0), shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    text = stringResource(R.string.goals_10_message),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFFFFA30F)
                )
                Text(
                    modifier = modifier
                        .padding(top = 6.dp),
                    text = "10일",
                    color = gray100,
                    style = MaterialTheme.typography.titleLarge
                )
            } else if (goals == 20) {
                Image(
                    painter = painterResource(R.drawable.goal_20),
                    contentDescription = "goal 20 image",
                    modifier = modifier.size(56.dp)
                )
                Text(
                    modifier = modifier
                        .padding(top = 14.dp)
                        .background(Color(0xFFFFF4E5), shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    text = stringResource(R.string.goals_20_message),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFFE65100)
                )
                Text(
                    modifier = modifier
                        .padding(top = 6.dp),
                    text = "20일",
                    color = gray100,
                    style = MaterialTheme.typography.titleLarge
                )
            } else if (goals == 30) {
                Image(
                    painter = painterResource(R.drawable.goal_30),
                    contentDescription = "goal 20 image",
                    modifier = modifier.size(56.dp)
                )
                Text(
                    modifier = modifier
                        .padding(top = 14.dp)
                        .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    text = stringResource(R.string.goals_30_message),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    modifier = modifier
                        .padding(top = 6.dp),
                    text = "30일",
                    color = gray100,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun GoalsComponentPreview() {
    SeeDayTheme {
        GoalsComponent(
            goals = 30,
            currentGoals = 0,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun GoalsComponentPreviewIsSameGoals() {
    SeeDayTheme {
        GoalsComponent(
            goals = 10,
            currentGoals = 10,
            onClick = {}
        )
    }
}
