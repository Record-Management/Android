package see.day.schedule.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.primaryColor
import see.day.schedule.R
import see.day.schedule.component.AlertSetting
import see.day.schedule.component.bottomsheet.AlertTime
import see.day.schedule.component.CalendarSetting
import see.day.schedule.component.ColorSetting
import see.day.schedule.component.LocationSetting
import see.day.schedule.component.MemoSetting
import see.day.schedule.component.bottomsheet.RepeatEndTime
import see.day.schedule.component.RepeatSetting
import see.day.schedule.component.bottomsheet.RepeatTime
import see.day.schedule.component.ScheduleTopBar
import see.day.ui.button.CompleteButton
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleDetailScreenRoot(onBack: () -> Unit, onClickPopHome: (Boolean) -> Unit) {
    var scheduleTitle by remember { mutableStateOf("") }

    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }

    var checkedTime by remember { mutableStateOf(AlertTime.NO) }

    var checkedRepeatTime by remember { mutableStateOf(RepeatTime.NO) }
    var checkedRepeatEndTime by remember { mutableStateOf<RepeatEndTime?>(null) }

    var locationText by remember { mutableStateOf("") }

    var checkedColor by remember { mutableStateOf(primaryColor) }

    var memoText by remember { mutableStateOf("") }

    ScheduleDetailScreen(
        modifier = Modifier.systemBarsPadding(),
        scheduleTitle = scheduleTitle,
        checkedColor = checkedColor,
        startDate = startDate,
        endDate = endDate,
        checkedTime = checkedTime,
        repeatTime = checkedRepeatTime,
        repeatEndTime = checkedRepeatEndTime,
        locationText = locationText,
        memoText = memoText,
        onBack = onBack,
        onClickPopHome = onClickPopHome,
        onScheduleTitleChange = { scheduleTitle = it },
        onStartDateChange = { startDate = it },
        onEndDateChange = { endDate = it },
        onCheckedTimeChange = { checkedTime = it },
        onRepeatTimeChange = { repeatTime, repeatEndTime ->
            checkedRepeatTime = repeatTime
            checkedRepeatEndTime = repeatEndTime
        },
        onLocationChange = { locationText = it },
        onColorChange = { checkedColor = it },
        onMemoChange = { memoText = it }
    )
}

@Composable
internal fun ScheduleDetailScreen(
    modifier: Modifier = Modifier,
    scheduleTitle: String,
    checkedColor: Color,
    startDate: LocalDate,
    endDate: LocalDate,
    checkedTime: AlertTime,
    repeatTime: RepeatTime,
    repeatEndTime: RepeatEndTime?,
    locationText: String,
    memoText: String,
    onBack: () -> Unit,
    onClickPopHome: (Boolean) -> Unit,
    onScheduleTitleChange: (String) -> Unit,
    onStartDateChange: (LocalDate) -> Unit,
    onEndDateChange: (LocalDate) -> Unit,
    onCheckedTimeChange: (AlertTime) -> Unit,
    onRepeatTimeChange: (RepeatTime, RepeatEndTime?) -> Unit,
    onLocationChange: (String) -> Unit,
    onColorChange: (Color) -> Unit,
    onMemoChange: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .imePadding(),
        topBar = {
            ScheduleTopBar(
                onClickCloseButton = onBack
            )
        },
        bottomBar = {
            CompleteButton(
                modifier = Modifier.navigationBarsPadding(),
                text = stringResource(see.day.ui.R.string.write_record_text),
                isEnabled = scheduleTitle.isNotBlank(),
                onClick = {
                    Timber.d(
                        "scheduleTitle: $scheduleTitle\n" +
                            "checkedColor: $checkedColor\n" +
                            "startDate: $startDate\n" +
                            "endDate: $endDate\n" +
                            "checkedTime: $checkedTime\n" +
                            "repeatTime: $repeatTime\n" +
                            "repeatEndTime: $repeatEndTime\n" +
                            "locationText: $locationText\n" +
                            "memoText: $memoText\n"
                    )
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // 색상하고 일정명 텍스트 필드
            ScheduleTitle(checkedColor, scheduleTitle, onScheduleTitleChange)
            CalendarSetting(
                modifier = Modifier.padding(top = 10.dp),
                startDate = startDate,
                endDate = endDate,
                setStartDate = onStartDateChange,
                setEndDate = onEndDateChange,
            )
            AlertSetting(
                modifier = Modifier.padding(top = 16.dp),
                checkedTime = checkedTime,
                onCheckedTimeChange = onCheckedTimeChange
            )
            RepeatSetting(
                modifier = Modifier.padding(top = 16.dp),
                startDate = startDate,
                repeatTime = repeatTime,
                repeatEndTime = repeatEndTime,
                onCheckedChange = onRepeatTimeChange
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(gray30),
            )
            LocationSetting(
                modifier = Modifier.padding(top = 10.dp),
                locationText = locationText,
                onLocationChange = onLocationChange,
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(gray30),
            )
            ColorSetting(
                modifier = Modifier.padding(top = 24.dp),
                selectedColor = checkedColor,
                onColorChange = onColorChange,
            )
            MemoSetting(
                modifier = Modifier.padding(top = 16.dp),
                text = memoText,
                onChangedText = onMemoChange,
            )
        }
    }
}

@Composable
private fun ScheduleTitle(
    checkedColor: Color,
    scheduleTitle: String,
    onScheduleTitleChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(height = 52.dp, width = 4.dp)
                .background(checkedColor, RoundedCornerShape(8.dp))
        )
        BasicTextField(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(height = 52.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(gray20)
                .padding(horizontal = 14.dp),
            maxLines = 1,
            value = scheduleTitle,
            textStyle = MaterialTheme.typography.displayMedium,
            onValueChange = onScheduleTitleChange,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (scheduleTitle.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.schedule_hint),
                            style = MaterialTheme.typography.displayMedium,
                            color = gray50
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

fun LocalDate.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (E)", java.util.Locale.KOREAN)
    return this.format(formatter)
}


@Preview
@Composable
private fun ScheduleScreenPreview() {
    var checkedTime by remember { mutableStateOf(AlertTime.NO) }
    SeeDayTheme {
        ScheduleDetailScreenRoot(
            onBack = {},
            onClickPopHome = {}
        )
    }
}
