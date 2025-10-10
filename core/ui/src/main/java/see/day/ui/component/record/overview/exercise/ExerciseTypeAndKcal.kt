package see.day.ui.component.record.overview.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.util.getIconRes
import see.day.model.record.exercise.ExerciseType

@Composable
internal fun ExerciseTypeAndKcal(
    modifier: Modifier = Modifier,
    exerciseType: ExerciseType,
    caloriesBurned: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier.size(66.dp).clip(CircleShape).background(Color.White)
        ) {
            Image(
                painter = painterResource(exerciseType.getIconRes),
                contentDescription = exerciseType.displayName,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = exerciseType.displayName,
            modifier = modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                "소모 칼로리",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                if (caloriesBurned.isEmpty()) "-" else "$caloriesBurned Kcal",
                style = MaterialTheme.typography.titleSmall
            )
        }

    }
}

@Preview
@Composable
private fun ExerciseTypeKcalPreview() {
    SeeDayTheme {
        ExerciseTypeAndKcal(
            exerciseType = ExerciseType.RUNNING,
            caloriesBurned = "100"
        )
    }
}

@Preview
@Composable
private fun ExerciseTypeKcalEmptyPreview() {
    SeeDayTheme {
        ExerciseTypeAndKcal(
            exerciseType = ExerciseType.RUNNING,
            caloriesBurned = ""
        )
    }
}
