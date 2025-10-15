package see.day.exercise.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray60
import see.day.exercise.R
import see.day.exercise.component.ExerciseSelectBottomSheet
import see.day.exercise.component.ExerciseTitle
import see.day.exercise.state.ExerciseDailyUiEffect
import see.day.exercise.state.ExerciseDetailUiEvent
import see.day.exercise.state.ExerciseDetailUiState
import see.day.exercise.util.ExerciseRecordPostType
import see.day.exercise.viewModel.ExerciseDetailViewModel
import see.day.model.record.RecordType
import see.day.model.record.exercise.ExerciseType
import see.day.ui.button.CompleteButton
import see.day.ui.dialog.DeleteRecordDialog
import see.day.ui.dialog.RecordDetailBackDialog
import see.day.ui.photo.RecordDetailPhotoRow
import see.day.ui.textField.HealthStat
import see.day.ui.textField.HealthStatInputField
import see.day.ui.textField.RecordWriteTextField
import see.day.ui.topbar.DetailRecordTopBar
import see.day.ui.topbar.EditMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreenRoot(
    viewModel: ExerciseDetailViewModel = hiltViewModel(),
    editType: ExerciseRecordPostType,
    onClickPopHome: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(editType) {
        viewModel.fetchData(editType)
    }


    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ExerciseDailyUiEffect.OnPopHome -> {
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
            onClickPopHome(false)
        }
    }
    if (openBackDialog) {
        RecordDetailBackDialog(
            modifier = Modifier,
            onDismiss = { openBackDialog = false },
            onBackRecordDetail = { onClickPopHome(false) },
            title = when (uiState.editMode) {
                is ExerciseDetailUiState.EditMode.Create -> {
                    see.day.ui.R.string.record_close_dialog_title
                }

                is ExerciseDetailUiState.EditMode.Edit -> {
                    see.day.ui.R.string.record_close_detail_dialog_title
                }
            },
            body = when (uiState.editMode) {
                is ExerciseDetailUiState.EditMode.Create -> {
                    see.day.ui.R.string.record_close_dialog_body
                }

                is ExerciseDetailUiState.EditMode.Edit -> {
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
                if (editMode is ExerciseDetailUiState.EditMode.Edit) {
                    viewModel.onEvent(ExerciseDetailUiEvent.DeleteRecord(editMode.recordId))
                }
            }
        )
    }

    var openSelectEmotionDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    if (openSelectEmotionDialog) {
        ExerciseSelectBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismiss = { openSelectEmotionDialog = false },
            onClickChangeExerciseType = { newExerciseType ->
                viewModel.onEvent(ExerciseDetailUiEvent.OnExerciseTypeChanged(newExerciseType))
            }
        )
    }
    ExerciseDetailScreen(
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        onClickExerciseImage = { openSelectEmotionDialog = true },
        onClickBackButton = {
            if (uiState.isEditing()) {
                openBackDialog = true
            } else {
                onClickPopHome(false)
            }
        },
        onClickDeleteButton = {
            openDeleteDialog = true
        }
    )
}

@Composable
internal fun ExerciseDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ExerciseDetailUiState,
    uiEvent: (ExerciseDetailUiEvent) -> Unit,
    onClickExerciseImage: () -> Unit,
    onClickBackButton: () -> Unit,
    onClickDeleteButton: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            DetailRecordTopBar(
                recordType = RecordType.EXERCISE,
                editMode = when (uiState.editMode) {
                    ExerciseDetailUiState.EditMode.Create -> EditMode.ADD
                    is ExerciseDetailUiState.EditMode.Edit -> EditMode.UPDATE
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
            ExerciseTitle(
                modifier = modifier.padding(top = 10.dp),
                exerciseType = uiState.exerciseType,
                onClickExerciseImage = onClickExerciseImage
            )
            HealthStatInputField(
                modifier = modifier.padding(top = 24.dp),
                healthStat = HealthStat.Kcal,
                text = uiState.caloriesBurned,
                onTextChanged = { newCaloriesBurned ->
                    uiEvent(ExerciseDetailUiEvent.OnCaloriesChanged(newCaloriesBurned))
                },
                focusManager = focusManager
            )
            HealthStatInputField(
                modifier = modifier.padding(top = 24.dp),
                healthStat = HealthStat.Time,
                text = uiState.exerciseTimeMinutes,
                onTextChanged = { newExerciseTime ->
                    uiEvent(ExerciseDetailUiEvent.OnExerciseTimeChanged(newExerciseTime))
                },
                focusManager = focusManager
            )
            HealthStatInputField(
                modifier = modifier.padding(top = 24.dp),
                healthStat = HealthStat.StepCount,
                text = uiState.stepCount,
                onTextChanged = { newStepCount ->
                    uiEvent(ExerciseDetailUiEvent.OnStepCountChanged(newStepCount))
                },
                focusManager = focusManager
            )
            HealthStatInputField(
                modifier = modifier.padding(top = 24.dp),
                healthStat = HealthStat.Weight,
                text = uiState.weight,
                onTextChanged = { newWeight ->
                    uiEvent(ExerciseDetailUiEvent.OnWeightChanged(newWeight))
                },
                focusManager = focusManager
            )
            Text(
                modifier = modifier.padding(top = 24.dp, bottom = 10.dp),
                text = "나의 하루",
                style = MaterialTheme.typography.displaySmall
            )
            RecordWriteTextField(
                modifier = modifier,
                text = uiState.dailyNote,
                placeHolder = see.day.ui.R.string.daily_place_holder,
                onChangedText = { newDailyNote ->
                    uiEvent(ExerciseDetailUiEvent.OnDailyNoteChanged(newDailyNote))
                }
            )
            RecordDetailPhotoRow(
                modifier = modifier,
                context = context,
                uris = uiState.imageUrls,
                onRemovePhotos = { photo ->
                    uiEvent(ExerciseDetailUiEvent.OnRemovePhoto(photo))
                },
                onClickAddPhotos = { photos ->
                    uiEvent(ExerciseDetailUiEvent.OnAddPhotos(photos))
                }
            )
            Text(
                modifier = modifier.padding(top = 10.dp),
                text = stringResource(R.string.max_photo_description),
                color = gray60,
                style = MaterialTheme.typography.labelSmall
            )
            CompleteButton(
                modifier = modifier
                    .padding(top = 80.dp)
                    .systemBarsPadding(),
                text = stringResource(
                    when(uiState.editMode) {
                        is ExerciseDetailUiState.EditMode.Create -> {
                            see.day.ui.R.string.write_record_text
                        }
                        is ExerciseDetailUiState.EditMode.Edit -> {
                            see.day.ui.R.string.modifiy_record_text
                        }
                    }

                ),
                isEnabled = uiState.canSubmit,
                onClick = {
                    uiEvent(ExerciseDetailUiEvent.OnSaveRecord)
                }
            )
        }

    }

}

@Preview
@Composable
private fun ExerciseDetailScreenPreview() {
    SeeDayTheme {
        ExerciseDetailScreen(uiState = ExerciseDetailUiState.init, uiEvent = {}, onClickExerciseImage = {}, onClickBackButton = {}, onClickDeleteButton = {})
    }
}
