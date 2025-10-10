package see.day.ui.component.record.overview.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray90
import see.day.ui.R

@Composable
internal fun ExerciseDetailStats(
    modifier: Modifier = Modifier,
    weight: String,
    exerciseTime: String,
    stepCount: String
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.detail_stat_title),
            style = MaterialTheme.typography.displaySmall.copy(color = gray90)
        )
        Row(
            modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
        ) {
            ExerciseDetailStat(
                detailStat = ExerciseDetailStatEnum.Weight,
                statPoint = weight
            )
            VerticalDivider(
                modifier = Modifier
                    .height(104.dp)
                    .width(1.dp),
                color = gray30
            )
            Spacer(modifier = Modifier.padding(start = 16.dp))
            ExerciseDetailStat(
                detailStat = ExerciseDetailStatEnum.ExerciseTime,
                statPoint = exerciseTime
            )
            VerticalDivider(
                modifier = Modifier
                    .height(104.dp)
                    .width(1.dp),
                color = gray30
            )
            Spacer(modifier = Modifier.padding(start = 16.dp))
            ExerciseDetailStat(
                detailStat = ExerciseDetailStatEnum.StepCount,
                statPoint = stepCount
            )
        }
    }
}

@Preview
@Composable
private fun ExerciseDetailStatsPreview() {
    SeeDayTheme {
        ExerciseDetailStats(
            weight = "100.5",
            exerciseTime = "50",
            stepCount = "20"
        )
    }
}
