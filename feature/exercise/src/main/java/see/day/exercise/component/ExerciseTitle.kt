package see.day.exercise.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.util.getIconRes
import see.day.model.record.exercise.ExerciseType

@Composable
internal fun ExerciseTitle(
    modifier: Modifier = Modifier,
    exerciseType: ExerciseType,
    onClickExerciseImage: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(exerciseType.getIconRes),
            contentDescription = exerciseType.displayName,
            modifier = Modifier
                .size(100.dp)
                .clickable { onClickExerciseImage() }
        )
        Text(
            text = exerciseType.displayName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 24.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
internal fun ExerciseTitlePreview() {
    SeeDayTheme {
        ExerciseTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            exerciseType = ExerciseType.GOLF,
            onClickExerciseImage = {}
        )
    }
}
