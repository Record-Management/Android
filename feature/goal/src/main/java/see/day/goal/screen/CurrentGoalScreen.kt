package see.day.goal.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme

@Composable
internal fun CurrentGoalScreenRoot(
    onBack : () -> Unit,
    userId: String
) {
    CurrentGoalScreen(
        onBack = onBack,
        userId = userId
    )
}

@Composable
internal fun CurrentGoalScreen(
    onBack: () -> Unit,
    userId: String
) {
    Column {
        Text(userId)
    }
}

@Preview
@Composable
private fun CurrentGoalScreenPreview() {
    SeeDayTheme {
        CurrentGoalScreen(
            onBack = {},
            userId = "Asdasdasd"
        )
    }
}
