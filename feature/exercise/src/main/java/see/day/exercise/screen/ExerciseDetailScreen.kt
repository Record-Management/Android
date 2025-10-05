package see.day.exercise.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.record.exercise.ExerciseType

@Composable
fun ExerciseDetailScreenRoot(exerciseType: ExerciseType) {
    ExerciseDetailScreen(exerciseType = exerciseType)
}

@Composable
internal fun ExerciseDetailScreen(modifier: Modifier = Modifier, exerciseType: ExerciseType) {
    Text(exerciseType.toString())
}

@Preview
@Composable
private fun ExerciseDetailScreenPreview() {
    SeeDayTheme {
        ExerciseDetailScreen(exerciseType = ExerciseType.GOLF)
    }
}
