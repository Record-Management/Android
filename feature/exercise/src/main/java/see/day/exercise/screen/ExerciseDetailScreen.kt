package see.day.exercise.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme
import see.day.exercise.util.ExerciseRecordPostType
import see.day.model.record.exercise.ExerciseType

@Composable
fun ExerciseDetailScreenRoot(editType : ExerciseRecordPostType) {
    ExerciseDetailScreen(editType = editType)
}

@Composable
internal fun ExerciseDetailScreen(modifier: Modifier = Modifier, editType: ExerciseRecordPostType) {
    Text(editType.toString())
}

@Preview
@Composable
private fun ExerciseDetailScreenPreview() {
    SeeDayTheme {
        ExerciseDetailScreen(editType = ExerciseRecordPostType.Write(ExerciseType.GOLF))
    }
}
