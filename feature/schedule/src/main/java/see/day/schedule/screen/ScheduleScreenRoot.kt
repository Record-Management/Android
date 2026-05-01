package see.day.schedule.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme
import see.day.schedule.component.AlertBottomSheet
import see.day.schedule.component.AlertTime

@Composable
fun ScheduleScreenRoot(onBack: () -> Unit, onClickPopHome: (Boolean) -> Unit) {
    var showAlertBottomSheet by remember { mutableStateOf(false) }
    var checkedTime by remember { mutableStateOf(AlertTime.NO) }

    if (showAlertBottomSheet) {
        AlertBottomSheet (
            onDismiss = {
                showAlertBottomSheet = false
            },
            checkedTime = checkedTime,
            onCheckedChange = { checkedTime = it }
        )
    }
    Column(
        modifier = Modifier.systemBarsPadding()
    ) {
        Button(
            onClick = {
                showAlertBottomSheet = true
            }
        ) {
            Text("알람 설정 바텀시트")
        }
        Text("반복 시간")
        Text(stringResource(checkedTime.textRes))
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    var checkedTime by remember { mutableStateOf(AlertTime.NO) }
    SeeDayTheme {
        ScheduleScreenRoot(
            onBack = {},
            onClickPopHome = {}
        )
    }
}
