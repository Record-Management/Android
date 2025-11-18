package see.day.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.ui.button.CompleteButton
import see.day.ui.component.GoalsComponent

@Composable
fun GoalsScreen(modifier: Modifier = Modifier, goalDays: Int, onComplete: (Int) -> Unit) {
    var currentGoalDays by remember { mutableStateOf(goalDays) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items(listOf(10, 20, 30)) { goal ->
                GoalsComponent(
                    goals = goal,
                    currentGoals = currentGoalDays,
                    onClick = { selectedGoal ->
                        if (selectedGoal == currentGoalDays) {
                            currentGoalDays = 0
                        } else {
                            currentGoalDays = selectedGoal
                        }
                    }
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))

        CompleteButton(
            modifier = modifier,
            text = "다음",
            isEnabled = currentGoalDays != 0,
            onClick = { onComplete(currentGoalDays) }
        )
    }
}

@Preview
@Composable
private fun GoalsScreenPreview() {
    SeeDayTheme {
        GoalsScreen(
            goalDays = 0,
            onComplete = {}
        )
    }
}
