package see.day.schedule.component.bottomsheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.Typography
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray40
import see.day.schedule.R
import see.day.ui.picker.WheelDatePicker
import see.day.ui.picker.WheelPickerDefaults
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RepeatTimeBottomSheet(
    modifier: Modifier = Modifier,
    repeatTime: RepeatTime,
    repeatEndTime: RepeatEndTime?,
    startDate: LocalDate,
    onDismiss: () -> Unit,
    onCheckedChange: (RepeatTime, RepeatEndTime?) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var bottomSheetCheckedRepeat by remember { mutableStateOf(repeatTime) }
    var bottomSheetRepeatEndTime by remember { mutableStateOf(repeatEndTime) }
    var timeSpinnerDisplayed by remember { mutableStateOf(false) }
    val dismissBottomSheet: () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    Column(
        modifier = modifier
    ) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = dismissBottomSheet,
            dragHandle = {},
            containerColor = Color.White,
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
                            .clickable { dismissBottomSheet() },
                    )

                    Text(
                        stringResource(R.string.repeat_set),
                        style = Typography.titleSmall,
                    )
                    Text(
                        modifier = Modifier.clickable {
                            onCheckedChange(bottomSheetCheckedRepeat, bottomSheetRepeatEndTime)
                            dismissBottomSheet()
                        },
                        text = stringResource(R.string.complete),
                        style = Typography.titleSmall
                    )
                }
                Column(
                    modifier = Modifier
                        .background(color = gray20)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    // 반복 시간 설정
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        RepeatTime.entries.forEachIndexed { index, repeatTime ->
                            RepeatText(
                                checkedRepeatTime = bottomSheetCheckedRepeat,
                                time = repeatTime,
                                onCheckedChange = { selectedTime ->
                                    bottomSheetCheckedRepeat = selectedTime
                                    if (selectedTime == RepeatTime.NO) {
                                        bottomSheetRepeatEndTime = null
                                    }
                                }
                            )
                            if (index < RepeatTime.entries.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = gray30,
                                )
                            }
                        }
                    }
                    // 반복 종료일 설정
                    AnimatedVisibility(
                        visible = bottomSheetCheckedRepeat != RepeatTime.NO,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .fillMaxWidth()
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.repeat_end_time),
                                    style = Typography.titleSmall,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Switch(
                                    checked = bottomSheetRepeatEndTime != null,
                                    onCheckedChange = { isChecked ->
                                        bottomSheetRepeatEndTime = if (isChecked) {
                                            timeSpinnerDisplayed = true
                                            RepeatEndTime(startDate.year, startDate.monthValue, startDate.dayOfMonth)
                                        } else {
                                            null
                                        }
                                    },
                                    colors = SwitchDefaults.colors().copy(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF2DC86F),
                                        checkedBorderColor = Color.White,
                                        uncheckedThumbColor = Color.White,
                                        uncheckedTrackColor = Color(0xFFD7D7D7),
                                        uncheckedBorderColor = Color.White,
                                    )
                                )
                            }
                            if (bottomSheetRepeatEndTime != null) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, gray20)
                                        .heightIn(min = 44.dp)
                                        .clickable { timeSpinnerDisplayed = !timeSpinnerDisplayed },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = bottomSheetRepeatEndTime?.dateStr ?: "",
                                        style = MaterialTheme.typography.titleSmall,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                if(timeSpinnerDisplayed) {
                                    WheelDatePicker(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        rowCount = 5,
                                        selectorProperties = WheelPickerDefaults.selectorProperties(
                                            color = gray40,
                                            shape = RoundedCornerShape(0),
                                            border = BorderStroke(0.dp, gray40)
                                        ),
                                        startDate = LocalDate.of(bottomSheetRepeatEndTime?.year ?: 2024, bottomSheetRepeatEndTime?.month ?: 1, bottomSheetRepeatEndTime?.dayOfMonth ?: 1),
                                        textStyle = MaterialTheme.typography.titleMedium,
                                        textColor = gray100
                                    ) { snappedDate ->
                                        bottomSheetRepeatEndTime = RepeatEndTime(snappedDate.year, snappedDate.monthValue, snappedDate.dayOfMonth)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(top = 15.dp, end = 16.dp, bottom = 16.dp).clickable { timeSpinnerDisplayed = false },
                                            text = stringResource(R.string.complete),
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                    }

                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun RepeatText(
    modifier: Modifier = Modifier,
    checkedRepeatTime: RepeatTime,
    time: RepeatTime,
    onCheckedChange: (RepeatTime) -> Unit,
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
                text = stringResource(time.textRes),
                style = Typography.displayMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            // 조건부로 이 컬럼의 높이가 달라지는 문제 수정
            Box(
                modifier = Modifier.size(20.dp),
                contentAlignment = Alignment.Center
            ) {
                if (checkedRepeatTime == time) {
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

@Preview
@Composable
private fun RepeatTimeBottomSheetPreview() {
    var checkedTime by remember { mutableStateOf(RepeatTime.NO) }
    var checkedEndTime by remember { mutableStateOf<RepeatEndTime?>(null) }
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
                text = "반복 설정 시간",
                style = Typography.displayMedium,
            )
            Text(
                text = stringResource(id = checkedTime.textRes),
                style = Typography.displayMedium,
            )
            Text(
                text = "반복 설정 끝나는 시간",
                style = Typography.displayMedium,
            )
            Text(
                text = checkedEndTime?.dateStr ?: "끝나는 시간 지정 안함",
                style = Typography.displayMedium,
            )
        }

        if (isBottomSheetOpen) {
            RepeatTimeBottomSheet(
                repeatTime = checkedTime,
                repeatEndTime = checkedEndTime,
                startDate = LocalDate.now(),
                onDismiss = { isBottomSheetOpen = false },
                onCheckedChange = { repeatTime, repeatEndTime ->
                    checkedTime = repeatTime
                    checkedEndTime = repeatEndTime
                }
            )
        }
    }
}

// 이것도 텍스트를 동일하게 사용해야함
// 반복 종료일 설정은 따로 두도록 해야함

enum class RepeatTime(val textRes: Int) {
    NO(R.string.no_repeat),
    DAILY(R.string.day_repeat),
    WEEKLY(R.string.week_repeat),
    MONTHLY(R.string.month_repeat),
    YEARLY(R.string.year_repeat)
}

data class RepeatEndTime(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val dateStr: String = "${year}년 ${month}월 ${dayOfMonth}일",
)
