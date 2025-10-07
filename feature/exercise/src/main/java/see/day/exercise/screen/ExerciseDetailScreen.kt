package see.day.exercise.screen

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
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray60
import see.day.exercise.R
import see.day.exercise.component.ExerciseSelectBottomSheet
import see.day.exercise.component.ExerciseTitle
import see.day.exercise.util.ExerciseRecordPostType
import see.day.model.record.RecordType
import see.day.model.record.exercise.ExerciseType
import see.day.ui.button.CompleteButton
import see.day.ui.photo.RecordDetailPhotoRow
import see.day.ui.textField.HealthStat
import see.day.ui.textField.HealthStatInputField
import see.day.ui.textField.RecordWriteTextField
import see.day.ui.topbar.DetailRecordTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreenRoot(editType: ExerciseRecordPostType) {
    var uiState by remember(editType) { mutableStateOf(editType) }

    var openSelectEmotionDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    if (openSelectEmotionDialog) {
        ExerciseSelectBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismiss = { openSelectEmotionDialog = false },
            onClickChangeExerciseType = { newExerciseType ->
                uiState = ExerciseRecordPostType.Write(newExerciseType)
            }
        )
    }
    ExerciseDetailScreen(editType = uiState, onClickExerciseImage = { openSelectEmotionDialog = true })
}

@Composable
internal fun ExerciseDetailScreen(modifier: Modifier = Modifier, editType: ExerciseRecordPostType, onClickExerciseImage: () -> Unit) {
    val (kcal, onKcalChanged) = remember { mutableStateOf("") }
    val (exerciseTime, onExerciseTimeChanged) = remember { mutableStateOf("") }
    val (stepCount, onStepCountChanged) = remember { mutableStateOf("") }
    val (weight, onWeightChanged) = remember { mutableStateOf("") }
    val (dailyNote, onDailyNoteChanged) = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            DetailRecordTopBar(
                recordType = RecordType.EXERCISE, onClickCloseButton = {})
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (editType is ExerciseRecordPostType.Write) {
                ExerciseTitle(
                    modifier = modifier.padding(top = 10.dp),
                    exerciseType = editType.exerciseType,
                    onClickExerciseImage = onClickExerciseImage
                )
                HealthStatInputField(
                    modifier = modifier.padding(top = 24.dp),
                    healthStat = HealthStat.Kcal,
                    text = kcal,
                    onTextChanged = onKcalChanged,
                    focusManager = focusManager
                )
                HealthStatInputField(
                    modifier = modifier.padding(top = 24.dp),
                    healthStat = HealthStat.Time,
                    text = exerciseTime,
                    onTextChanged = onExerciseTimeChanged,
                    focusManager = focusManager
                )
                HealthStatInputField(
                    modifier = modifier.padding(top = 24.dp),
                    healthStat = HealthStat.StepCount,
                    text = stepCount,
                    onTextChanged = onStepCountChanged,
                    focusManager = focusManager
                )
                HealthStatInputField(
                    modifier = modifier.padding(top = 24.dp),
                    healthStat = HealthStat.Weight,
                    text = weight,
                    onTextChanged = onWeightChanged,
                    focusManager = focusManager
                )
                Text(
                    modifier = modifier.padding(top = 24.dp, bottom = 10.dp),
                    text = "나의 하루",
                    style = MaterialTheme.typography.displaySmall
                )
                RecordWriteTextField(
                    modifier = modifier,
                    text = dailyNote,
                    placeHolder = see.day.ui.R.string.daily_place_holder,
                    onChangedText = onDailyNoteChanged
                )
                RecordDetailPhotoRow(
                    modifier = modifier,
                    context = context,
                    uris = listOf(),
                    onRemovePhotos = { photo ->
                    },
                    onClickAddPhotos = { photos ->
                    }
                )
                Text(
                    modifier = modifier.padding(top = 10.dp),
                    text = stringResource(R.string.max_photo_description),
                    color = gray60,
                    style = MaterialTheme.typography.labelSmall
                )
                CompleteButton(
                    modifier = modifier.padding(top = 80.dp).systemBarsPadding(),
                    text = stringResource(
                        see.day.ui.R.string.write_record_text
                    ),
                    isEnabled = true,
                    onClick = {
                    }
                )
            }
        }

    }

}

@Preview
@Composable
private fun ExerciseDetailScreenPreview() {
    SeeDayTheme {
        ExerciseDetailScreen(editType = ExerciseRecordPostType.Write(ExerciseType.GOLF), onClickExerciseImage = {})
    }
}
