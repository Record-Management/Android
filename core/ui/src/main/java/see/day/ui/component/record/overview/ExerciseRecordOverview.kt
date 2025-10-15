package see.day.ui.component.record.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray10
import see.day.designsystem.theme.gray30
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.record.RecordType
import see.day.model.record.exercise.ExerciseType
import see.day.ui.component.record.overview.exercise.ExerciseDailyNote
import see.day.ui.component.record.overview.exercise.ExerciseDetailStats
import see.day.ui.component.record.overview.exercise.ExerciseTypeAndKcal

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseRecordOverview(
    modifier: Modifier = Modifier,
    exerciseRecord: ExerciseRecordDetail,
    onClickItem: (RecordType, String) -> Unit,
    onClickLongItem: (RecordType, String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = { onClickItem(RecordType.EXERCISE, exerciseRecord.id) },
                onLongClick = { onClickLongItem(RecordType.EXERCISE, exerciseRecord.id) }
            )
            .background(gray10)
            .padding(16.dp)
    ) {
        ExerciseTypeAndKcal(
            modifier,
            exerciseType = exerciseRecord.exerciseType,
            caloriesBurned = exerciseRecord.caloriesBurned,
        )
        Spacer(
            modifier = modifier
                .padding(top = 16.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(gray30)
        )
        ExerciseDetailStats(modifier = modifier.padding(top = 16.dp), weight = exerciseRecord.weight, exerciseTime = exerciseRecord.exerciseTimeMinutes, stepCount = exerciseRecord.stepCount)
        Spacer(
            modifier = modifier
                .padding(top = 16.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(gray30)
        )

        ExerciseDailyNote(
            modifier = Modifier.padding(top = 16.dp),
            dailyNote = exerciseRecord.dailyNote,
            recordTime = exerciseRecord.formatTimeTo12Hour()
        )
    }
}

@Preview
@Composable
private fun ExerciseRecordOverviewPreview() {
    SeeDayTheme {
        ExerciseRecordOverview(
            exerciseRecord = ExerciseRecordDetail(
                "",
                type = RecordType.EXERCISE,
                recordDate = "2025-10-25",
                createdAt = "2025-10-25",
                updatedAt = "2025-10-25",
                recordTime = "13:13",
                exerciseType = ExerciseType.GOLF,
                imageUrls = listOf(),
                exerciseTimeMinutes = "100",
                stepCount = "100",
                weight = "70.5",
                caloriesBurned = "100",
                dailyNote = "너무너무 행복한 하루였다.",
            ),
            onClickItem = { type, id -> },
            onClickLongItem = { type, id -> }
        )
    }
}
