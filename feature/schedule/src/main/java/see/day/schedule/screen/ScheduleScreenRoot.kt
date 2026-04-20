package see.day.schedule.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme

@Composable
fun ScheduleScreenRoot(onBack: () -> Unit, onClickPopHome: (Boolean) -> Unit) {
    Text("Hello")
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    SeeDayTheme {
        ScheduleScreenRoot(
            onBack = {},
            onClickPopHome = {}
        )
    }
}
