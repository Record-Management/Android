package see.day.schedule.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.primaryColor
import see.day.schedule.R
import see.day.schedule.component.AlertBottomSheet
import see.day.schedule.component.AlertTime
import see.day.schedule.component.ColorPaletteBottomSheet
import see.day.schedule.component.RepeatEndTime
import see.day.schedule.component.RepeatTime
import see.day.schedule.component.RepeatTimeBottomSheet
import java.time.LocalDate

@Composable
fun ScheduleScreenRoot(onBack: () -> Unit, onClickPopHome: (Boolean) -> Unit) {
    var showAlertBottomSheet by remember { mutableStateOf(false) }
    var checkedTime by remember { mutableStateOf(AlertTime.NO) }

    var showRepeatTimeBottomSheet by remember { mutableStateOf(false) }
    var checkedRepeatTime by remember { mutableStateOf(RepeatTime.NO) }
    var checkedRepeatEndTime by remember { mutableStateOf<RepeatEndTime?>(null) }

    var showColorPaletteBottomSheet by remember { mutableStateOf(false) }
    var checkedColor by remember { mutableStateOf(primaryColor) }

    if (showAlertBottomSheet) {
        AlertBottomSheet(
            onDismiss = {
                showAlertBottomSheet = false
            },
            checkedTime = checkedTime,
            onCheckedChange = { checkedTime = it }
        )
    }
    if (showRepeatTimeBottomSheet) {
        RepeatTimeBottomSheet(
            repeatTime = checkedRepeatTime,
            repeatEndTime = checkedRepeatEndTime,
            startDate = LocalDate.now(),
            onDismiss = {
                showRepeatTimeBottomSheet = false
            },
            onCheckedChange = { repeatTime, repeatEndTime ->
                checkedRepeatTime = repeatTime
                checkedRepeatEndTime = repeatEndTime
                showRepeatTimeBottomSheet = false
            }
        )
    }

    if (showColorPaletteBottomSheet) {
        ColorPaletteBottomSheet(
            selectedColor = checkedColor,
            onDismiss = {
                showColorPaletteBottomSheet = false
            },
            onColorSelected = { checkedColor = it }
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

        Button(
            onClick = {
                showRepeatTimeBottomSheet = true
            }
        ) {
            Text("반복 시간")
        }
        Text("반복 시간")
        Text(stringResource(checkedRepeatTime.textRes))
        Text("반복 종료일 : ${checkedRepeatEndTime?.dateStr ?: ""}")

        Button(
            onClick = {
                showColorPaletteBottomSheet = true
            }
        ) {
            Text("색상")
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = checkedColor, shape = CircleShape)
        ) {
        }
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
