package see.day.schedule

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme

@Composable
fun ScheduleScreen() {
    Text("Hello")
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    SeeDayTheme {
        ScheduleScreen()
    }
}
