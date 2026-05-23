package see.day.ui.component.record.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.Typography
import see.day.designsystem.theme.gray60
import see.day.designsystem.theme.primaryColor
import see.day.model.record.RecordType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// [TODO] 추후 홈 카드에 부착 예정 (파라미터는 변경될 수 있음)
@Composable
fun ScheduleRecordOverview(
    modifier: Modifier = Modifier,
    scheduleId : String,
    title: String,
    startDate: LocalDate,
    endDate: LocalDate,
    color: Color,
    memo: String,
    onClickItem : (RecordType, String) -> Unit,
    onClickLongItem : (String) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .combinedClickable(
                onClick = { onClickItem(RecordType.SCHEDULE, scheduleId) },
                onLongClick = { onClickLongItem(scheduleId) }
            )
    ) {
        Spacer(
            modifier = Modifier
                .width(4.dp)
                .height(70.dp)
                .background(
                    color = color,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .height(70.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = Typography.titleSmall)
            Text(modifier = Modifier.padding(top = 6.dp), text = formatScheduleDateRange(startDate, endDate), style = Typography.headlineMedium.copy(color = gray60))
            Text(modifier = Modifier.padding(top = 4.dp), text = memo.ifEmpty { "-" }, style = Typography.headlineMedium.copy(color = gray60), maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

private fun formatScheduleDateRange(
    startDate: LocalDate,
    endDate: LocalDate,
): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy. M. d (E)", Locale.KOREAN)
    return "${startDate.format(formatter)} - ${endDate.format(formatter)}"
}

@Preview
@Composable
private fun ScheduleRecordOverviewPreview() {
    SeeDayTheme {
        ScheduleRecordOverview(
            modifier = Modifier.padding(horizontal = 16.dp),
            scheduleId = "",
            title = "장보기",
            startDate = LocalDate.now().minusDays(2),
            endDate = LocalDate.now(),
            color = primaryColor,
            memo = "만두사기 만두사기 만두사기 만두사기만두사기 만두사기 만두사기 만두사기 만두사기만두사기 만두사기 만두사기 만두사기 만두사기만두사기 만두사기 만두사기 만두사기 만두사기만두사기 만두사기 만두사기 만두사기 만두사기만두사기 ",
            onClickItem = { recordType, s -> },
            onClickLongItem = {},
        )
    }
}


