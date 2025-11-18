package see.day.goal.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme

@Composable
internal fun ResetGoalScreenRoot(

) {
    ResetGoalScreen()
}

@Composable
internal fun ResetGoalScreen(
    modifier: Modifier = Modifier
) {
    Column {
        Text("목표 재설정 화면")
    }
}

@Preview
@Composable
private fun ResetGoalScreenPreview() {
    SeeDayTheme {
        ResetGoalScreen()
    }
}
