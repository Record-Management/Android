package see.day.ui.component.record.overview.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray70
import see.day.ui.R

@Composable
internal fun RowScope.ExerciseDetailStat(
    modifier: Modifier = Modifier,
    detailStat: ExerciseDetailStatEnum,
    statPoint: String
) {
    Column(
        modifier = Modifier
            .heightIn(104.dp)
            .weight(1f),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(detailStat.iconRes),
                contentDescription = detailStat.displayName
            )
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = detailStat.displayName,
            style = MaterialTheme.typography.headlineLarge.copy(color = gray70)
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = if (statPoint.isEmpty()) "- ${detailStat.unit}" else "$statPoint ${detailStat.unit}",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

enum class ExerciseDetailStatEnum(val displayName: String, val iconRes: Int, val unit: String) {
    Weight("몸무게", R.drawable.exercise_weight, "Kg"), ExerciseTime("운동 시간", R.drawable.exercise_time, "분"), StepCount("걸음 수", R.drawable.exercise_stepcount, "걸음")
}

@Preview
@Composable
private fun ExerciseDetailStatPreview() {
    SeeDayTheme {
        Row {
            ExerciseDetailStat(
                detailStat = ExerciseDetailStatEnum.Weight,
                statPoint = "100.5"
            )
        }

    }
}

@Preview
@Composable
private fun ExerciseDetailStatEmptyPointPreview() {
    SeeDayTheme {
        Row {
            ExerciseDetailStat(
                detailStat = ExerciseDetailStatEnum.ExerciseTime,
                statPoint = ""
            )
        }

    }
}
