package see.day.notification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray60
import see.day.designsystem.theme.gray70
import see.day.model.record.RecordType
import see.day.notification.util.TimeFormatUtil
import see.day.notification.util.TimeFormatUtil.daysBefore
import see.day.notification.util.TimeFormatUtil.hourBefore
import see.day.notification.util.historyMessage
import see.day.util.getIcon

@Composable
internal fun HistoryCard(
    modifier: Modifier,
    recordType: RecordType,
    isChecked: Boolean,
    relativeTime: String,
    onClickCard: (RecordType, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 95.dp)
            .clickable { onClickCard(recordType, relativeTime) }
            .background(if (isChecked) Color.White else Color(0xFFFFF5EC))
            .then(modifier)
    ) {
        Box(
            modifier = Modifier.size(24.dp).border((0.72).dp, gray30, CircleShape)
        ) {
            Image(
                painter = painterResource(recordType.getIcon()),
                contentDescription = recordType.title,
                modifier = Modifier.size(16.dp).align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = recordType.title,
                    style = MaterialTheme.typography.labelMedium.copy(color = gray70)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = relativeTime,
                    style = MaterialTheme.typography.headlineMedium.copy(color = gray60)
                )
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = recordType.historyMessage(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
private fun HistoryCardPreviewFiveHourBefore() {
    val time = TimeFormatUtil.getRelativeTimeString(hourBefore(5))
    SeeDayTheme {
        HistoryCard(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            recordType = RecordType.DAILY,
            isChecked = false,
            relativeTime = time,
            onClickCard = { type, relativeTime ->}
        )
    }
}

@Preview
@Composable
private fun HistoryCardPreviewBeforeThreeDays() {
    val time = TimeFormatUtil.getRelativeTimeString(daysBefore(3))
    SeeDayTheme {
        HistoryCard(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            recordType = RecordType.DAILY,
            isChecked = false,
            relativeTime = time,
            onClickCard = { type, relativeTime ->}
        )
    }
}

@Preview
@Composable
private fun HistoryCardsPreview() {
    val time = TimeFormatUtil.getRelativeTimeString(daysBefore(3))
    SeeDayTheme {
        Column {
            RecordType.entries.forEach { recordType ->
                HistoryCard(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    recordType = recordType,
                    isChecked = false,
                    relativeTime = time,
                    onClickCard = { type, relativeTime ->}
                )
            }
        }
    }
}
