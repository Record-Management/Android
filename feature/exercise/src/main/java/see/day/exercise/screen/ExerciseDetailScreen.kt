package see.day.exercise.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.exercise.component.ExerciseTitle
import see.day.exercise.util.ExerciseRecordPostType
import see.day.model.record.RecordType
import see.day.model.record.exercise.ExerciseType
import see.day.ui.topbar.DetailRecordTopBar

@Composable
fun ExerciseDetailScreenRoot(editType: ExerciseRecordPostType) {
    ExerciseDetailScreen(editType = editType)
}

@Composable
internal fun ExerciseDetailScreen(modifier: Modifier = Modifier, editType: ExerciseRecordPostType) {
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
                .verticalScroll(rememberScrollState())
        ) {
            if(editType is ExerciseRecordPostType.Write) {
                ExerciseTitle(
                    modifier = modifier.padding(top = 10.dp),
                    exerciseType = editType.exerciseType,
                    onClickExerciseImage = {}
                )
            }
        }

    }

}

@Preview
@Composable
private fun ExerciseDetailScreenPreview() {
    SeeDayTheme {
        ExerciseDetailScreen(editType = ExerciseRecordPostType.Write(ExerciseType.GOLF))
    }
}
