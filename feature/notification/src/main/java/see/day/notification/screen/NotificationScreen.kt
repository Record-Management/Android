package see.day.notification.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.record.RecordType
import see.day.notification.R
import see.day.notification.component.HistoryCard
import see.day.notification.state.NotificationHistory
import see.day.notification.util.TimeFormatUtil
import see.day.ui.topbar.CommonAppBar

@Composable
internal fun NotificationScreenRoute(
    onBack: () -> Unit
) {
    NotificationScreen(
        onBack = onBack,
        notificationHistories = listOf()
    )
}

@Composable
internal fun NotificationScreen(
    modifier: Modifier = Modifier,
    notificationHistories: List<NotificationHistory>,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .systemBarsPadding(),
        topBar = {
            CommonAppBar(
                title = R.string.notification_title,
                onClickBackButton = onBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(notificationHistories) { history ->
                HistoryCard(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    recordType = history.recordType,
                    relativeTime = history.relativeTime,
                    isChecked = history.isChecked,
                    onClickCard = { type, time -> },
                )
            }
        }
    }

}


@Preview
@Composable
private fun NotificationScreenPreview() {
    SeeDayTheme {
        NotificationScreen(
            onBack = {},
            notificationHistories = getSampleNotificationHistory(3)
        )
    }
}

private fun getSampleNotificationHistory(count: Int): List<NotificationHistory> {
    return (0 until count).map { num ->
        NotificationHistory(
            recordType = RecordType.entries[num%3],
            relativeTime = TimeFormatUtil.getRelativeTimeString(TimeFormatUtil.daysBefore(num.toLong())),
            isChecked = num%2 == 0
        )
    }
}
