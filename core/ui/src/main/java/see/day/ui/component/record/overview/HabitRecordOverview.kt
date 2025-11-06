package see.day.ui.component.record.overview

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray10
import see.day.designsystem.util.getIconRes
import see.day.model.calendar.HabitRecordDetail
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitRecordOverView(
    modifier: Modifier = Modifier,
    habitRecord: HabitRecordDetail,
    onClickItem: (RecordType, String) -> Unit,
    onClickLongItem: (RecordType, String) -> Unit,
    onClickChecked: (String, Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = { onClickItem(RecordType.HABIT, habitRecord.id) },
                onLongClick = { onClickLongItem(RecordType.HABIT, habitRecord.id) }
            )
            .background(gray10)
            .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(habitRecord.habitType.getIconRes),
                contentDescription = habitRecord.habitType.displayName,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = habitRecord.habitType.displayName,
            modifier = modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(44.dp)
                .clickable { onClickChecked(habitRecord.id, !habitRecord.isCompleted) }
        ) {
            Image(
                painter = painterResource(if (habitRecord.isCompleted) see.day.designsystem.R.drawable.ic_checked else see.day.designsystem.R.drawable.ic_unchecked),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun HabitRecordOverViewPreview() {
    val context = LocalContext.current
    var habitRecord by remember { mutableStateOf(HabitRecordDetail("", RecordType.HABIT, "", "", "", "", HabitType.SAVING, true, "", "", true, true)) }
    SeeDayTheme {
        HabitRecordOverView(
            habitRecord = habitRecord,
            onClickItem = { type, id ->
                Toast.makeText(context, "onItemClick", Toast.LENGTH_SHORT).show()
            },
            onClickLongItem = { type, id ->
                Toast.makeText(context, "onClickLongItem", Toast.LENGTH_SHORT).show()
            },
            onClickChecked = { id, isChecked ->
                habitRecord = habitRecord.copy(isCompleted = isChecked)
            }
        )
    }
}
