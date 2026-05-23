package see.day.schedule.screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.primaryColor
import see.day.model.record.RecordType
import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.schedule.R
import see.day.schedule.component.AlertSetting
import see.day.schedule.component.CalendarSetting
import see.day.schedule.component.ColorSetting
import see.day.schedule.component.LocationSetting
import see.day.schedule.component.MemoSetting
import see.day.schedule.component.RepeatSetting
import see.day.schedule.component.ScheduleTopBar
import see.day.schedule.state.ScheduleDetailUiEffect
import see.day.schedule.state.ScheduleDetailUiEvent
import see.day.schedule.state.ScheduleDetailUiState
import see.day.schedule.state.SchedulePostType
import see.day.schedule.viewModel.ScheduleDetailViewModel
import see.day.ui.button.CompleteButton
import see.day.ui.dialog.RecordDetailBackDialog
import see.day.ui.topbar.DetailRecordTopBar
import see.day.ui.topbar.EditMode
import see.day.util.toColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleDetailScreenRoot(
    viewModel: ScheduleDetailViewModel = hiltViewModel(),
    editType: SchedulePostType,
    onClickPopHome: (Boolean) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(editType) {
        viewModel.fetchData(editType)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ScheduleDetailUiEffect.NavigateToHome -> {
                    onClickPopHome(effect.isUpdated)
                }
            }
        }
    }

    var openBackDialog by remember { mutableStateOf(false) }

    BackHandler {
        if (uiState.isEditing()) {
            openBackDialog = true
        } else {
            if (uiState.editMode is ScheduleDetailUiState.EditMode.Create) {
//                viewModel.writeHabitRecordCancelLog()
            }
            onClickPopHome(false)
        }
    }

    if (openBackDialog) {
        RecordDetailBackDialog(
            modifier = Modifier,
            onDismiss = { openBackDialog = false },
            onBackRecordDetail = {
                if (uiState.editMode is ScheduleDetailUiState.EditMode.Create) {
//                    viewModel.writeHabitRecordCancelLog()
                }
                onClickPopHome(false)
            },
            title = when (uiState.editMode) {
                is ScheduleDetailUiState.EditMode.Create -> {
                    see.day.ui.R.string.record_close_dialog_title
                }

                is ScheduleDetailUiState.EditMode.Edit -> {
                    see.day.ui.R.string.record_close_detail_dialog_title
                }
            },
            body = when (uiState.editMode) {
                is ScheduleDetailUiState.EditMode.Create -> {
                    see.day.ui.R.string.record_close_dialog_body
                }

                is ScheduleDetailUiState.EditMode.Edit -> {
                    see.day.ui.R.string.record_close_detail_dialog_body
                }
            }
        )
    }

    ScheduleDetailScreen(
        modifier = Modifier.systemBarsPadding(),
        uiState = uiState,
        onAction = viewModel::onAction,
        onClickBackButton = {
            if (uiState.isEditing()) {
                openBackDialog = true
            } else {
                onClickPopHome(false)
            }
        },
    )
}

@Composable
internal fun ScheduleDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ScheduleDetailUiState,
    onAction: (ScheduleDetailUiEvent) -> Unit,
    onClickBackButton: () -> Unit,
) {
    Scaffold(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .imePadding(),
        topBar = {
            DetailRecordTopBar(
                recordType = RecordType.SCHEDULE,
                editMode = when (uiState.editMode) {
                    ScheduleDetailUiState.EditMode.Create -> EditMode.ADD
                    is ScheduleDetailUiState.EditMode.Edit -> EditMode.UPDATE
                },
                onClickCloseButton = onClickBackButton,
                onClickDeleteButton = {
                    // TODO 삭제하기 api 추가후 수정
                }
            )
        },
        bottomBar = {
            CompleteButton(
                modifier = Modifier.navigationBarsPadding(),
                text = when (uiState.editMode) {
                    is ScheduleDetailUiState.EditMode.Create -> stringResource(see.day.ui.R.string.write_record_text)
                    is ScheduleDetailUiState.EditMode.Edit -> stringResource(see.day.ui.R.string.modifiy_record_text)
                },
                isEnabled = uiState.canSubmit,
                onClick = {
                    onAction(ScheduleDetailUiEvent.OnSaveSchedule)
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // 색상하고 일정명 텍스트 필드
            ScheduleTitle(
                checkedColor = uiState.color.toColor(),
                scheduleTitle = uiState.title,
                onScheduleTitleChange = {
                    onAction(ScheduleDetailUiEvent.OnTitleChanged(it))
                })
            CalendarSetting(
                modifier = Modifier.padding(top = 24.dp),
                startDate = uiState.startDate,
                endDate = uiState.endDate,
                setStartDate = { onAction(ScheduleDetailUiEvent.OnStartDateChanged(it)) },
                setEndDate = {  onAction(ScheduleDetailUiEvent.OnEndDateChanged(it)) },
            )
            AlertSetting(
                modifier = Modifier.padding(top = 16.dp),
                checkedTime = uiState.alertType,
                checkedTimeHour = uiState.notificationCustomHours ?: 9,
                checkedTimeMinute = uiState.notificationCustomMinutes ?: 0,
                onCheckedTimeChange = { alertTime, hours, minutes ->
                    onAction(ScheduleDetailUiEvent.OnAlertTimeChanged(alertTime, hours, minutes))
                }
            )
            RepeatSetting(
                modifier = Modifier.padding(top = 16.dp),
                startDate = uiState.startDate,
                repeatTime = uiState.repeatType,
                repeatEndTime = uiState.repeatEndsOn,
                onCheckedChange = { repeatTime, repeatEndsOn ->
                    onAction(ScheduleDetailUiEvent.OnRepeatTypeChanged(repeatTime, repeatEndsOn))
                }
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
                locationText = uiState.location,
                onLocationChange = { onAction(ScheduleDetailUiEvent.OnLocationChanged(it)) },
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
                selectedColor = uiState.color,
                onColorChange = {
                    onAction(ScheduleDetailUiEvent.OnColorChanged(it))
                },
            )
            MemoSetting(
                modifier = Modifier.padding(top = 16.dp),
                text = uiState.memo,
                onChangedText = {
                    onAction(ScheduleDetailUiEvent.OnMemoChanged(it))
                },
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
    var checkedTime by remember { mutableStateOf(AlertTime.NONE) }
    SeeDayTheme {
        ScheduleDetailScreenRoot(
            editType = SchedulePostType.Write,
            onClickPopHome = {}
        )
    }
}
