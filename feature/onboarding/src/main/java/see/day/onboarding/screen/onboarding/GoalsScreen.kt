package see.day.onboarding.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.onboarding.component.GoalsComponent
import see.day.onboarding.component.NextButton
import see.day.onboarding.state.onboarding.OnboardingUiEvent

@Composable
internal fun GoalsScreen(
    modifier: Modifier = Modifier,
    goals: Int,
    onComplete: (OnboardingUiEvent) -> Unit
) {
    var currentGoals by remember { mutableStateOf(goals) }

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(10, 20, 30).forEach {  goal ->
                GoalsComponent(
                    goals = goal,
                    currentGoals = currentGoals,
                    onClick = { selectedGoal ->
                        if(selectedGoal == currentGoals) {
                            currentGoals = 0
                        }else {
                            currentGoals = selectedGoal
                        }

                    }
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))

        NextButton(
            modifier = modifier,
            isEnabled = currentGoals != 0,
            onClick = { onComplete(OnboardingUiEvent.EnterGoal(currentGoals)) }
        )
    }
}

@Preview
@Composable
private fun GoalsScreenPreview() {
    SeeDayTheme {
        GoalsScreen(
            goals = 0,
            onComplete = {}
        )
    }
}
