package see.day.daily.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.daily.R
import see.day.daily.component.EmotionAndDate
import see.day.daily.component.EmotionSelectBottomSheet
import see.day.daily.state.DailyDetailUiEffect
import see.day.daily.state.DailyDetailUiEvent
import see.day.daily.state.DailyDetailUiState
import see.day.daily.util.DailyRecordPostType
import see.day.daily.viewModel.DailyDetailViewModel
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray60
import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion
import see.day.ui.button.CompleteButton
import see.day.ui.dialog.ConfirmDialog
import see.day.ui.dialog.RecordDetailBackDialog
import see.day.ui.photo.RecordDetailPhotoRow
import see.day.ui.textField.RecordWriteTextField
import see.day.ui.topbar.DetailRecordTopBar
import see.day.ui.topbar.EditMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DailyDetailScreenRoot(modifier: Modifier = Modifier, viewModel: DailyDetailViewModel = hiltViewModel(), dailyRecordPostType: DailyRecordPostType, onClickPopHome: (Boolean) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(dailyRecordPostType) {
        viewModel.fetchData(dailyRecordPostType)
    }
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (val effect = it) {
                is DailyDetailUiEffect.OnPopHome -> {
                    onClickPopHome(effect.isUpdated)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    var openSelectEmotionDialog by remember { mutableStateOf(false) }
    var openBackDialog by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BackHandler {
        if (uiState.isEditing()) {
            openBackDialog = true
        } else {
            onClickPopHome(false)
        }
    }
    if (openBackDialog) {
        RecordDetailBackDialog(
            modifier = modifier,
            onDismiss = { openBackDialog = false },
            onBackRecordDetail = { onClickPopHome(false) },
            title = when (uiState.editMode) {
                is DailyDetailUiState.EditMode.Create -> {
                    see.day.ui.R.string.record_close_dialog_title
                }

                is DailyDetailUiState.EditMode.Edit -> {
                    see.day.ui.R.string.record_close_detail_dialog_title
                }
            },
            body = when (uiState.editMode) {
                is DailyDetailUiState.EditMode.Create -> {
                    see.day.ui.R.string.record_close_dialog_body
                }

                is DailyDetailUiState.EditMode.Edit -> {
                    see.day.ui.R.string.record_close_detail_dialog_body
                }
            }
        )
    }

    var openDeleteDialog by remember { mutableStateOf(false) }
    if (openDeleteDialog) {
        ConfirmDialog(
            onDismiss = { openDeleteDialog = false },
            onClickConfirmButton = {
                val editMode = uiState.editMode
                if (editMode is DailyDetailUiState.EditMode.Edit) {
                    viewModel.onEvent(DailyDetailUiEvent.DeleteRecord(editMode.recordId))
                }
            }
        )
    }
    val onClickChangeEmotion: (DailyEmotion) -> Unit = { emotion ->
        viewModel.onEvent(DailyDetailUiEvent.OnChangeDailyEmotion(emotion))
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (openSelectEmotionDialog) {
        EmotionSelectBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismiss = { openSelectEmotionDialog = false },
            onClickChangeEmotion = onClickChangeEmotion
        )
    }

    DailyDetailScreen(
        modifier = modifier,
        uiState = uiState,
        onClickBackButton = {
            if (uiState.isEditing()) {
                openBackDialog = true
            } else {
                onClickPopHome(false)
            }
        },
        onClickDeleteButton = { openDeleteDialog = true },
        onClickEmotion = { openSelectEmotionDialog = true },
        uiEvent = viewModel::onEvent
    )
}

@Composable
internal fun DailyDetailScreen(modifier: Modifier = Modifier, uiState: DailyDetailUiState, onClickBackButton: () -> Unit, onClickDeleteButton: () -> Unit, onClickEmotion: () -> Unit, uiEvent: (DailyDetailUiEvent) -> Unit) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier
            .systemBarsPadding()
            .background(Color.White),
        topBar = {
            DetailRecordTopBar(
                modifier = modifier,
                recordType = RecordType.DAILY,
                editMode = when (uiState.editMode) {
                    DailyDetailUiState.EditMode.Create -> EditMode.ADD
                    is DailyDetailUiState.EditMode.Edit -> EditMode.UPDATE
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
        ) {
            EmotionAndDate(
                modifier = modifier,
                emotion = uiState.emotion,
                currentDate = uiState.dateTime.formatFullDate(),
                currentTime = uiState.dateTime.formatFullTime(),
                onClickEmotion = onClickEmotion
            )
            RecordWriteTextField(
                modifier = modifier.padding(top = 24.dp),
                text = uiState.text,
                placeHolder = R.string.record_daily_place_holder,
                onChangedText = { changedText ->
                    uiEvent(DailyDetailUiEvent.OnChangedText(changedText))
                }
            )
            RecordDetailPhotoRow(
                modifier = modifier,
                context = context,
                uris = uiState.photos,
                onRemovePhotos = { photo ->
                    uiEvent(DailyDetailUiEvent.OnRemovePhoto(photo))
                },
                { photos ->
                    uiEvent(DailyDetailUiEvent.OnAddPhotos(photos))
                }
            )
            Text(
                modifier = modifier.padding(top = 10.dp),
                text = stringResource(R.string.photo_limit_text),
                color = gray60,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = modifier.weight(1f))
            CompleteButton(
                text = stringResource(
                    when (uiState.editMode) {
                        is DailyDetailUiState.EditMode.Create -> {
                            see.day.ui.R.string.write_record_text
                        }

                        is DailyDetailUiState.EditMode.Edit -> {
                            see.day.ui.R.string.modifiy_record_text
                        }
                    }
                ),
                isEnabled = uiState.canSubmit,
                onClick = {
                    uiEvent(DailyDetailUiEvent.OnSaveRecord)
                }
            )
        }
    }
}

@Preview
@Composable
private fun DailyDetailWriteScreen() {
    SeeDayTheme {
        DailyDetailScreen(
            onClickBackButton = { },
            onClickDeleteButton = {},
            onClickEmotion = { },
            uiState = DailyDetailUiState.init,
            uiEvent = {}
        )
    }
}
