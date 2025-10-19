package see.day.habit.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray30
import see.day.designsystem.util.getIconRes
import see.day.habit.R
import see.day.habit.component.HabitAlertComponent
import see.day.habit.component.HabitSelectBottomSheet
import see.day.habit.state.HabitDetailUiEffect
import see.day.habit.state.HabitDetailUiEvent
import see.day.habit.state.HabitDetailUiState
import see.day.habit.state.HabitRecordPostType
import see.day.habit.viewModel.HabitDetailViewModel
import see.day.model.record.RecordType
import see.day.ui.button.CompleteButton
import see.day.ui.component.TypeTitle
import see.day.ui.dialog.DeleteRecordDialog
import see.day.ui.dialog.RecordDetailBackDialog
import see.day.ui.textField.RecordWriteTextField
import see.day.ui.topbar.DetailRecordTopBar
import see.day.ui.topbar.EditMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HabitDetailScreenRoot(
    viewModel: HabitDetailViewModel = hiltViewModel(),
    editType: HabitRecordPostType,
    onClickPopHome: (Boolean) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(editType) {
        viewModel.fetchData(editType)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is HabitDetailUiEffect.OnPopHome -> {
                    onClickPopHome(effect.isUpdated)
                }
            }
        }
    }

    var openSelectEmotionDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    if (openSelectEmotionDialog) {
        HabitSelectBottomSheet(
            sheetState = sheetState,
            onDismiss = { openSelectEmotionDialog = false },
            onClickHabit = { newHabitType ->
                viewModel.onEvent(HabitDetailUiEvent.OnHabitTypeChanged(newHabitType))
            }
        )
    }

    var openBackDialog by remember { mutableStateOf(false) }

    BackHandler {
        if (uiState.isEditing()) {
            openBackDialog = true
        } else {
            onClickPopHome(false)
        }
    }

    if (openBackDialog) {
        RecordDetailBackDialog(
            modifier = Modifier,
            onDismiss = { openBackDialog = false },
            onBackRecordDetail = { onClickPopHome(false) },
            title = when (uiState.editMode) {
                is HabitDetailUiState.EditMode.Create -> {
                    see.day.ui.R.string.record_close_dialog_title
                }

                is HabitDetailUiState.EditMode.Edit -> {
                    see.day.ui.R.string.record_close_detail_dialog_title
                }
            },
            body = when (uiState.editMode) {
                is HabitDetailUiState.EditMode.Create -> {
                    see.day.ui.R.string.record_close_dialog_body
                }

                is HabitDetailUiState.EditMode.Edit -> {
                    see.day.ui.R.string.record_close_detail_dialog_body
                }
            }
        )
    }

    var openDeleteDialog by remember { mutableStateOf(false) }
    if (openDeleteDialog) {
        DeleteRecordDialog(
            onDismiss = { openDeleteDialog = false },
            onClickDeleteButton = {
                val editMode = uiState.editMode
                if (editMode is HabitDetailUiState.EditMode.Edit) {
                    viewModel.onEvent(HabitDetailUiEvent.DeleteRecord(editMode.recordId))
                }
            }
        )
    }


    HabitDetailScreen(
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        onClickHabitTitle = { openSelectEmotionDialog = true },
        onClickBackButton = {
            if (uiState.isEditing()) {
                openBackDialog = true
            } else {
                onClickPopHome(false)
            }
        },
        onClickDeleteButton = { openDeleteDialog = true }
    )
}

@Composable
internal fun HabitDetailScreen(
    modifier: Modifier = Modifier,
    uiState: HabitDetailUiState,
    uiEvent: (HabitDetailUiEvent) -> Unit,
    onClickHabitTitle: () -> Unit,
    onClickBackButton: () -> Unit,
    onClickDeleteButton: () -> Unit
) {
    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            DetailRecordTopBar(
                recordType = RecordType.HABIT,
                editMode = when (uiState.editMode) {
                    HabitDetailUiState.EditMode.Create -> EditMode.ADD
                    is HabitDetailUiState.EditMode.Edit -> EditMode.UPDATE
                },
                onClickCloseButton = onClickBackButton,
                onClickDeleteButton = onClickDeleteButton
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TypeTitle(
                modifier = modifier.padding(top = 10.dp),
                typeIcon = uiState.habitType.getIconRes,
                typeName = uiState.habitType.displayName,
                onClickType = onClickHabitTitle
            )
            HabitAlertComponent(
                modifier = modifier
                    .padding(top = 24.dp),
                isChecked = uiState.notificationEnabled,
                hour = uiState.hour,
                minute = uiState.minute,
                onClickSwitch = { enabled ->
                    uiEvent(HabitDetailUiEvent.OnNotificationEnabledChanged(enabled))
                },
                onTimeChanged = { hour, minute ->
                    uiEvent(HabitDetailUiEvent.OnAlertTimeChanged(hour, minute))
                }
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .border(1.dp, gray30)
            )
            Row(
                modifier = Modifier.padding(top = 24.dp),
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
            RecordWriteTextField(
                modifier = Modifier.padding(top = 10.dp),
                placeHolder = R.string.memo,
                text = uiState.memo,
                onChangedText = { changedText ->
                    uiEvent(HabitDetailUiEvent.OnMemoChanged(changedText))
                }
            )
            CompleteButton(
                modifier = Modifier
                    .padding(top = 80.dp)
                    .systemBarsPadding(),
                text = stringResource(
                    when (uiState.editMode) {
                        is HabitDetailUiState.EditMode.Create -> {
                            see.day.ui.R.string.write_record_text
                        }

                        is HabitDetailUiState.EditMode.Edit -> {
                            see.day.ui.R.string.modifiy_record_text
                        }
                    }
                ),
                isEnabled = uiState.canSubmit,
                onClick = {
                    uiEvent(HabitDetailUiEvent.OnSaveRecord)
                }
            )
        }
    }
}

@Preview
@Composable
private fun HabitDetailScreenPreview() {
    SeeDayTheme {
        HabitDetailScreen(
            uiState = HabitDetailUiState.init,
            uiEvent = {},
            onClickHabitTitle = {},
            onClickBackButton = {},
            onClickDeleteButton = {}
        )
    }
}
