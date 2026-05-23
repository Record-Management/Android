package see.day.schedule.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.gray70
import see.day.model.schedule.AlertTime
import see.day.schedule.R
import see.day.schedule.component.bottomsheet.AlertBottomSheet
import see.day.schedule.component.bottomsheet.formatTimeToKorean
import see.day.schedule.component.bottomsheet.getTextRes

@Composable
internal fun AlertSetting(
    modifier: Modifier = Modifier,
    checkedTime: AlertTime,
    checkedTimeHour: Int,
    checkedTimeMinute: Int,
    onCheckedTimeChange: (AlertTime, Int, Int) -> Unit,
) {
    var isShowAlertBottomSheet by remember { mutableStateOf(false) }

    if (isShowAlertBottomSheet) {
        AlertBottomSheet(
            onDismiss = {
                isShowAlertBottomSheet = false
            },
            checkedTime = checkedTime,
            notificationHour = checkedTimeHour,
            notificationMinute = checkedTimeMinute,
            onCheckedChange = onCheckedTimeChange
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isShowAlertBottomSheet = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_notification),
            contentDescription = "알림 설정",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "알림",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = if (checkedTime == AlertTime.CUSTOM) {
                "시간 설정, " + formatTimeToKorean(checkedTimeHour, checkedTimeMinute)
            } else {
                stringResource(checkedTime.getTextRes())
            },
            style = MaterialTheme.typography.labelSmall.copy(
                color = gray70
            )
        )
        Icon(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "알림 설정",
            modifier = Modifier
                .size(20.dp)
                .padding(start = 6.dp),
            tint = Color.Unspecified
        )
    }
}
