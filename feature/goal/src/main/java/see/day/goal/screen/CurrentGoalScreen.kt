package see.day.goal.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.goal.R
import see.day.ui.topbar.ClosableTopBar

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
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    userId: String
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
        Text(
            modifier = Modifier.padding(innerPadding),
            text = userId
        )
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
