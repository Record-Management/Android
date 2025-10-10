package see.day.ui.component.record.overview.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray80
import see.day.designsystem.theme.gray90
import see.day.ui.R

@Composable
internal fun ExerciseDailyNote(
    modifier: Modifier = Modifier,
    dailyNote: String,
    recordTime: String
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.exercise_record_title),
            style = MaterialTheme.typography.displaySmall.copy(color = gray90)
        )
        Text(
            modifier = Modifier.padding(top = 10.dp).heightIn(min = 63.dp),
            text = dailyNote,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = recordTime,
            style = MaterialTheme.typography.headlineMedium.copy(gray80)
        )
    }
}

@Preview
@Composable
private fun ExerciseDailyNotePreview() {
    SeeDayTheme {
        ExerciseDailyNote(
            dailyNote = "정말정말 즐거운 야호",
            recordTime = "오전 11:09"
        )
    }
}

@Preview
@Composable
private fun ExerciseFullDailyNotePreview() {
    SeeDayTheme {
        ExerciseDailyNote(
            dailyNote = "안녕하세요\n반갑습니다\n진짜로 반갑습니다\n정말 반갑습니다",
            recordTime = "오후 1:10"
        )
    }
}
