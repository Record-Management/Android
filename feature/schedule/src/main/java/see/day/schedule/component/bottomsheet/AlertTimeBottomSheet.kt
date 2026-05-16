package see.day.schedule.component.bottomsheet

import AlertTime
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import kotlinx.coroutines.launch
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.Typography
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray40
import see.day.schedule.R
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlertBottomSheet(
    modifier: Modifier = Modifier,
    checkedTime: AlertTime,
    notificationHour: Int = 9,
    notificationMinute: Int = 0,
    onDismiss: () -> Unit,
    onCheckedChange: (AlertTime, Int, Int) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var bottomSheetCheckedTime by remember { mutableStateOf(checkedTime) }
    var currentNotificationHour by remember { mutableIntStateOf(notificationHour) }
    var currentNotificationMinute by remember { mutableIntStateOf(notificationMinute) }
    val dismissBottomSheet: (isChanged: Boolean) -> Unit = { shouldApplyChange ->
        coroutineScope.launch {
            sheetState.hide()
            if (shouldApplyChange) {
                onCheckedChange(bottomSheetCheckedTime, currentNotificationHour, currentNotificationMinute)
            }
            onDismiss()
        }
    }

    Column(
        modifier = modifier
    ) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { dismissBottomSheet(false) },
            dragHandle = {},
            containerColor = Color.White,
            sheetGesturesEnabled = false,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(see.day.ui.R.drawable.ic_arrow_left),
                        contentDescription = "뒤로 가기 버튼",
                        tint = gray100,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { dismissBottomSheet(false) },
                    )

                    Text(
                        stringResource(R.string.alert_set),
                        style = Typography.titleSmall,
                    )
                    Text(
                        modifier = Modifier.clickable {
                            dismissBottomSheet(true)
                        },
                        text = stringResource(R.string.complete),
                        style = Typography.titleSmall
                    )
                }
                Column(
                    modifier = Modifier
                        .background(color = gray20)
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        AlertTime.entries.forEachIndexed { index, alertTime ->
                            AlertText(
                                checkedTime = bottomSheetCheckedTime,
                                time = alertTime,
                                onCheckedChange = { selectedTime ->
                                    bottomSheetCheckedTime = selectedTime
                                }
                            )
                            if (index < AlertTime.entries.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = gray30,
                                )
                            }
                        }
                    }
                    if(bottomSheetCheckedTime == AlertTime.CUSTOM) {
                        CustomAlertTimeSetting(
                            modifier = Modifier.padding(16.dp),
                            notificationHour = currentNotificationHour,
                            notificationMinute = currentNotificationMinute,
                            onNotificationTimeChanged = { hour, minute ->
                                currentNotificationHour = hour
                                currentNotificationMinute = minute
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertText(
    modifier: Modifier = Modifier,
    checkedTime: AlertTime,
    time: AlertTime,
    onCheckedChange: (AlertTime) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(time) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(time.getTextRes()),
                style = Typography.displayMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            // 조건부로 이 컬럼의 높이가 달라지는 문제 수정
            Box(
                modifier = Modifier.size(20.dp),
                contentAlignment = Alignment.Center
            ) {
                if (checkedTime == time) {
                    Icon(
                        painter = painterResource(R.drawable.ic_checked),
                        contentDescription = "선택됨",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomAlertTimeSetting(
    modifier: Modifier,
    notificationHour: Int,
    notificationMinute: Int,
    onNotificationTimeChanged: (Int, Int) -> Unit,
) {
    var isShowTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(8.dp), color = Color.White)
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(R.string.time_set),
            style = Typography.titleSmall
        )
        Text(
            modifier = Modifier.fillMaxWidth().border(width = 1.dp, shape =  RoundedCornerShape(10.dp), color = gray20).clickable {
                isShowTimePicker = true
            }.padding(vertical = 12.dp),
            text = formatTimeToKorean(notificationHour, notificationMinute),
            style = Typography.titleSmall,
            textAlign = TextAlign.Center
        )
        if (isShowTimePicker) {
            WheelTimePicker(
                timeFormat = TimeFormat.AM_PM,
                startTime = LocalTime.of(notificationHour, notificationMinute),
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
                    onNotificationTimeChanged(time.hour, time.minute)
                },
                textStyle = Typography.titleMedium,
                textColor = gray100
            )
            Text(
                text = stringResource(R.string.complete),
                style = Typography.titleSmall,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        isShowTimePicker = false
                    }
            )
        }
    }
}

@Preview
@Composable
private fun AlertTimeBottomSheetPreview() {
    var checkedTime by remember { mutableStateOf(AlertTime.NONE) }
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    SeeDayTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = { isBottomSheetOpen = true }
            ) {
                Text(text = "알림 설정 바텀시트")
            }
            Text(
                text = "알림 설정 시간",
                style = Typography.displayMedium,
            )
            Text(
                text = stringResource(id = checkedTime.getTextRes()),
                style = Typography.displayMedium,
            )
        }

        if (isBottomSheetOpen) {
            AlertBottomSheet(
                checkedTime = checkedTime,
                onDismiss = { isBottomSheetOpen = false },
                onCheckedChange = { checkedNewTime, hour, minute ->
                    checkedTime = checkedNewTime
                }
            )
        }
    }
}

@Preview
@Composable
private fun AlertTextPreview() {
    var checkedTime by remember { mutableStateOf(AlertTime.ONE_DAY_BEFORE) }

    SeeDayTheme {
        AlertText(
            checkedTime = checkedTime,
            time = AlertTime.ONE_DAY_BEFORE,
            onCheckedChange = { checkedTime = it }
        )
    }
}

fun AlertTime.getTextRes(): Int {
    return when (this) {
        AlertTime.NONE -> R.string.alert_time_no
        AlertTime.ONE_DAY_BEFORE -> R.string.alert_time_one_day
        AlertTime.TWO_DAYS_BEFORE -> R.string.alert_time_two_day
        AlertTime.CUSTOM -> R.string.alert_time_custom
    }
}

/**
 * 시간(0-23)과 분(0-59)을 받아서 "오전/오후 HH:MM" 형식으로 변환
 * @param hour 0-23 사이의 시간
 * @param minute 0-59 사이의 분 (기본값 0)
 * @return "오전 03:00" 또는 "오후 09:30" 형식의 문자열
 */
@SuppressLint("DefaultLocale")
fun formatTimeToKorean(hour: Int, minute: Int = 0): String {
    require(hour in 0..23) { "Hour must be between 0 and 23" }
    require(minute in 0..59) { "Minute must be between 0 and 59" }

    val period = if (hour < 12) "오전" else "오후"
    val displayHour = when (hour) {
        0 -> 12
        in 1..12 -> hour
        else -> hour - 12
    }

    return String.format("%s %02d:%02d", period, displayHour, minute)
}
