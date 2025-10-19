package see.day.habit.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray40
import see.day.habit.R
import java.time.LocalTime

@Composable
internal fun HabitAlertComponent(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    hour: Int,
    minute: Int,
    onClickSwitch: (Boolean) -> Unit,
    onTimeChanged: (hour: Int, minute: Int) -> Unit
) {
    var isSpinnerDisplay by remember(isChecked) { mutableStateOf(isChecked) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = "알림 이미지",
                modifier = Modifier.size(24.dp)
            )
            Text(
                "알림",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 6.dp)
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isChecked,
                onCheckedChange = onClickSwitch,
                colors = SwitchDefaults.colors().copy(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF2DC86F),
                    checkedBorderColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFD7D7D7),
                    uncheckedBorderColor = Color.White,
                ),
            )
        }
        if(isChecked) {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, gray20)
                    .heightIn(min = 44.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = formatTime(hour, minute),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
        if(isSpinnerDisplay) {
            WheelTimePicker(
                timeFormat = TimeFormat.AM_PM,
                startTime = LocalTime.of(hour, minute),
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    color = gray40,
                    shape = RoundedCornerShape(0),
                    border = BorderStroke(0.dp, gray40)
                ),
                size = DpSize(200.dp, 161.dp),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                rowCount = 7,
                onSnappedTime = { time ->
                    onTimeChanged(time.hour, time.minute)
                },
                textStyle = MaterialTheme.typography.titleMedium,
                textColor = gray100
            )
            Text(
                "완료",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.End).clickable {
                    isSpinnerDisplay = false
                }
            )
        }

    }
}

private fun formatTime(hour: Int, minute: Int): String {
    val period = if (hour < 12) "오전" else "오후"
    val displayHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    return String.format("%s %d:%02d", period, displayHour, minute)
}

@Preview
@Composable
private fun HabitAlertComponentPreview() {
    val (isChecked, onClickSwitch) = remember { mutableStateOf(false) }
    var hour by remember { mutableStateOf(10) }
    var minute by remember { mutableStateOf(0) }

    val onTimeChanged: (Int, Int) -> Unit = { currentHour, currentMinute ->
        hour = currentHour
        minute = currentMinute
    }
    SeeDayTheme {
        HabitAlertComponent(
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp),
            isChecked = isChecked,
            hour = hour,
            minute = minute,
            onClickSwitch = onClickSwitch,
            onTimeChanged = onTimeChanged
        )
    }
}
