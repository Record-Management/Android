package see.day.goal.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.goal.state.reset.GoalResetStep
import see.day.goal.state.reset.getProgress

@Composable
internal fun ResetGoalTopBar(modifier: Modifier = Modifier, step: GoalResetStep, onClickBackButton: () -> Unit) {
    Column(
        modifier = modifier.height(60.dp)
    ) {
        if (step != GoalResetStep.RECORD) {
            Image(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp)
                    .clickable { onClickBackButton() },
                painter = painterResource(see.day.ui.R.drawable.ic_arrow_left),
                contentDescription = "뒤로가기 버튼"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        LinearProgressIndicator(
            progress = { step.getProgress() },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun ResetGoalTopBarRecordPreview() {
    SeeDayTheme {
        ResetGoalTopBar(
            step = GoalResetStep.RECORD,
            onClickBackButton = {}
        )
    }
}

@Preview
@Composable
private fun ResetGoalTopBarDaysPreview() {
    SeeDayTheme {
        ResetGoalTopBar(
            step = GoalResetStep.DAY,
            onClickBackButton = {}
        )
    }
}
