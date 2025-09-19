package see.day.daily.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme

@Composable
internal fun DailyScreenRoot(
    modifier: Modifier = Modifier
) {
    DailyScreen(modifier)
}

@Composable
internal fun DailyScreen(
    modifier: Modifier = Modifier
) {
    Text("Daily Screen")
}

@Preview
@Composable
private fun DailyScreenPreview() {
    SeeDayTheme {
        DailyScreen()
    }
}
