package see.day.goal.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.goal.R
import see.day.goal.state.CurrentGoalUiState
import see.day.ui.topbar.ClosableTopBar

@Composable
internal fun CurrentGoalScreenRoot(
    onBack : () -> Unit,
    userId: String
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
        Text(
            modifier = Modifier.padding(innerPadding),
            text = uiState.userId
        )
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
