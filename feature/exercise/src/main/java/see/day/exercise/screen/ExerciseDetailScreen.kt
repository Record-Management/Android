package see.day.exercise.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray60
import see.day.designsystem.util.getIconRes
import see.day.exercise.R
import see.day.exercise.component.ExerciseSelectBottomSheet
import see.day.exercise.state.ExerciseDailyUiEffect
import see.day.exercise.state.ExerciseDetailUiEvent
import see.day.exercise.state.ExerciseDetailUiState
import see.day.exercise.util.ExerciseRecordPostType
import see.day.exercise.viewModel.ExerciseDetailViewModel
import see.day.model.record.RecordType
import see.day.ui.button.CompleteButton
import see.day.ui.component.TypeTitle
import see.day.ui.dialog.ConfirmDialog
import see.day.ui.dialog.RecordDetailBackDialog
import see.day.ui.photo.RecordDetailPhotoRow
import see.day.ui.textField.HealthStat
import see.day.ui.textField.HealthStatInputField
import see.day.ui.textField.RecordWriteTextField
import see.day.ui.topbar.DetailRecordTopBar
import see.day.ui.topbar.EditMode

@Composable
fun ExerciseDetailScreenRoot(
    viewModel: ExerciseDetailViewModel = hiltViewModel(),
    editType: ExerciseRecordPostType,
    onClickPopHome: (Boolean) -> Unit
) {
    val context = LocalContext.current
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

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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

    ExerciseDetailScreen(
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        onClickBackButton = {
            if (uiState.isEditing()) {
                openBackDialog = true
            } else {
                onClickPopHome(false)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExerciseDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ExerciseDetailUiState,
    uiEvent: (ExerciseDetailUiEvent) -> Unit,
    onClickBackButton: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var openSelectExerciseDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    if (openSelectExerciseDialog) {
        ExerciseSelectBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismiss = { openSelectExerciseDialog = false },
            onClickChangeExerciseType = { newExerciseType ->
                uiEvent(ExerciseDetailUiEvent.OnExerciseTypeChanged(newExerciseType))
            }
        )
    }

    var openDeleteDialog by remember { mutableStateOf(false) }
    if (openDeleteDialog) {
        ConfirmDialog(
            onDismiss = { openDeleteDialog = false },
            onClickConfirmButton = {
                val editMode = uiState.editMode
                if (editMode is ExerciseDetailUiState.EditMode.Edit) {
                    uiEvent(ExerciseDetailUiEvent.DeleteRecord(editMode.recordId))
                }
            }
        )
    }

    Scaffold(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .imePadding(),
        topBar = {
            DetailRecordTopBar(
                recordType = RecordType.EXERCISE,
                editMode = when (uiState.editMode) {
                    ExerciseDetailUiState.EditMode.Create -> EditMode.ADD
                    is ExerciseDetailUiState.EditMode.Edit -> EditMode.UPDATE
                },
                onClickCloseButton = onClickBackButton,
                onClickDeleteButton = {
                    openDeleteDialog = true
                }
            )
        },
        bottomBar = {
            CompleteButton(
                modifier = Modifier.navigationBarsPadding(),
                text = stringResource(
                    when (uiState.editMode) {
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
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            TypeTitle(
                modifier = Modifier.padding(top = 10.dp),
                typeIcon = uiState.exerciseType.getIconRes,
                typeName = uiState.exerciseType.displayName,
                onClickType = {
                    openSelectExerciseDialog = true
                }
            )
            Row(
                modifier = Modifier.padding(top = 24.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    "운동 기록",
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    "(1개 이상 필수 입력)",
                    style = MaterialTheme.typography.labelSmall.copy(color = gray50),
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            HealthStatInputField(
                modifier = Modifier.padding(top = 16.dp),
                healthStat = HealthStat.Kcal,
                text = uiState.caloriesBurned,
                onTextChanged = { newCaloriesBurned ->
                    uiEvent(ExerciseDetailUiEvent.OnCaloriesChanged(newCaloriesBurned))
                },
                focusManager = focusManager
            )
            HealthStatInputField(
                modifier = Modifier.padding(top = 24.dp),
                healthStat = HealthStat.Time,
                text = uiState.exerciseTimeMinutes,
                onTextChanged = { newExerciseTime ->
                    uiEvent(ExerciseDetailUiEvent.OnExerciseTimeChanged(newExerciseTime))
                },
                focusManager = focusManager
            )
            HealthStatInputField(
                modifier = Modifier.padding(top = 24.dp),
                healthStat = HealthStat.StepCount,
                text = uiState.stepCount,
                onTextChanged = { newStepCount ->
                    uiEvent(ExerciseDetailUiEvent.OnStepCountChanged(newStepCount))
                },
                focusManager = focusManager
            )
            HealthStatInputField(
                modifier = Modifier.padding(top = 24.dp),
                healthStat = HealthStat.Weight,
                text = uiState.weight,
                onTextChanged = { newWeight ->
                    uiEvent(ExerciseDetailUiEvent.OnWeightChanged(newWeight))
                },
                focusManager = focusManager
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(gray30)
            )
            Text(
                modifier = Modifier.padding(top = 24.dp, bottom = 10.dp),
                text = "나의 하루",
                style = MaterialTheme.typography.displaySmall
            )
            RecordWriteTextField(
                modifier = Modifier,
                text = uiState.dailyNote,
                placeHolder = see.day.ui.R.string.daily_place_holder,
                onChangedText = { newDailyNote ->
                    uiEvent(ExerciseDetailUiEvent.OnDailyNoteChanged(newDailyNote))
                }
            )
            RecordDetailPhotoRow(
                modifier = Modifier,
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
                modifier = Modifier.padding(top = 10.dp, bottom = 25.dp),
                text = stringResource(R.string.max_photo_description),
                color = gray60,
                style = MaterialTheme.typography.labelSmall
            )
        }

    }
}

@Preview
@Composable
private fun ExerciseDetailScreenPreview() {
    SeeDayTheme {
        ExerciseDetailScreen(uiState = ExerciseDetailUiState.init, uiEvent = {}, onClickBackButton = {})
    }
}
