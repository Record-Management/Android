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
import see.day.schedule.R
import see.day.schedule.component.bottomsheet.RepeatEndTime
import see.day.schedule.component.bottomsheet.RepeatTime
import see.day.schedule.component.bottomsheet.RepeatTimeBottomSheet
import java.time.LocalDate

@Composable
internal fun RepeatSetting(
    modifier: Modifier = Modifier,
    startDate: LocalDate,
    repeatTime: RepeatTime,
    repeatEndTime: RepeatEndTime?,
    onCheckedChange: (RepeatTime, RepeatEndTime?) -> Unit,
) {
    var isShowRepeatBottomSheet by remember { mutableStateOf(false) }

    if (isShowRepeatBottomSheet) {
        RepeatTimeBottomSheet(
            startDate = startDate,
            repeatTime = repeatTime,
            repeatEndTime = repeatEndTime,
            onCheckedChange = onCheckedChange,
            onDismiss = {
                isShowRepeatBottomSheet = false
            }
        )
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isShowRepeatBottomSheet = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_repeat),
            contentDescription = "반복 설정",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "반복",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(repeatTime.textRes) + if (repeatEndTime != null) ", ${repeatEndTime.dateStr} 종료" else "",
            style = MaterialTheme.typography.labelSmall.copy(
                color = gray70
            )
        )
        Icon(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "반복 설정",
            modifier = Modifier
                .size(20.dp)
                .padding(start = 6.dp),
            tint = Color.Unspecified
        )
    }
}
