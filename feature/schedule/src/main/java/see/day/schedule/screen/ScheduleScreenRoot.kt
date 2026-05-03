package see.day.schedule.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray40
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray60
import see.day.designsystem.theme.gray70
import see.day.designsystem.theme.primaryColor
import see.day.schedule.R
import see.day.schedule.component.AlertBottomSheet
import see.day.schedule.component.AlertSetting
import see.day.schedule.component.AlertTime
import see.day.schedule.component.CalendarSetting
import see.day.schedule.component.ColorPaletteBottomSheet
import see.day.schedule.component.RepeatEndTime
import see.day.schedule.component.RepeatSetting
import see.day.schedule.component.RepeatTime
import see.day.schedule.component.RepeatTimeBottomSheet
import see.day.schedule.component.ScheduleTopBar
import see.day.ui.button.CompleteButton
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
                    // TODO 클릭시 이벤트 필요
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
            LocationSetting(locationText, onLocationChange)
            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(gray30),
            )
            ColorSetting(
                selectedColor = checkedColor,
                onColorChange = onColorChange,
            )
            MemoTextField(
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

@Composable
private fun LocationSetting(
    locationText: String,
    onLocationChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_compass),
            contentDescription = "위치 설정",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = stringResource(id = R.string.location),
            style = MaterialTheme.typography.titleSmall,
        )
        BasicTextField(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(height = 52.dp)
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 14.dp),
            value = locationText,
            textStyle = MaterialTheme.typography.displayMedium,
            maxLines = 1,
            onValueChange = onLocationChange,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (locationText.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.location_hint),
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

@Composable
private fun ColorSetting(
    selectedColor: Color,
    onColorChange: (Color) -> Unit,
) {
    var isShowColorBottomSheet by remember { mutableStateOf(false) }

    if (isShowColorBottomSheet) {
        ColorPaletteBottomSheet(
            selectedColor = selectedColor,
            onColorSelected = onColorChange,
            onDismiss = {
                isShowColorBottomSheet = false
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .clickable {
                isShowColorBottomSheet = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_palette),
            contentDescription = "색상 설정",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "색상",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(selectedColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
        }
        Icon(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "색상 설정",
            modifier = Modifier
                .size(20.dp)
                .padding(start = 6.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun MemoTextField(modifier: Modifier = Modifier, text: String, onChangedText: (String) -> Unit) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            painter = painterResource(R.drawable.ic_article),
            contentDescription = stringResource(R.string.memo),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(R.string.memo),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 6.dp)
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(251.dp)
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(gray20)
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                if(newValue.length <= 1000) {
                    onChangedText(newValue)
                }
            },
            textStyle = MaterialTheme.typography.displayMedium.copy(color = gray100),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
                .padding(top = 14.dp, bottom = 45.dp),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = "메모",
                        style = MaterialTheme.typography.displayMedium,
                        color = gray50
                    )
                }
                innerTextField()
            }
        )
        Text(
            text = "${text.length} / 1000",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 14.dp, bottom = 14.dp),
            color = if (text.isEmpty()) gray60 else gray100
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
