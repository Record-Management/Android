package see.day.goal.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.goal.state.reset.GoalResetStep

@Composable
internal fun ColumnScope.ResetGoalTitleDescription(modifier: Modifier = Modifier, step: GoalResetStep) {
    Image(
        modifier = Modifier
            .padding(top = 30.dp)
            .size(30.dp),
        painter = painterResource(step.iconRes),
        contentDescription = "목표 재설정 ${step.ordinal} 번째 아이콘"
    )
    Text(
        modifier = Modifier.padding(top = 10.dp),
        text = stringResource(step.titleRes),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview
@Composable
private fun ResetGoalTitleDescriptionPreview() {
    SeeDayTheme {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            ResetGoalTitleDescription(
                step = GoalResetStep.DAY
            )
        }
    }
}
