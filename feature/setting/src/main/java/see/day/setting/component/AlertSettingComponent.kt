package see.day.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray90
import see.day.setting.R

@Composable
internal fun AlertSettingComponent(
    modifier: Modifier = Modifier,
    onClickAppAlert: () -> Unit,
    onClickRecordsAlert: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.alert_setting),
            style = MaterialTheme.typography.titleSmall
        )
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppAlertComponent(Modifier, onClickAppAlert)
            RecordsAlertComponent(Modifier, onClickRecordsAlert)
        }
    }
}

@Composable
private fun AppAlertComponent(
    modifier: Modifier = Modifier,
    onClickAppAlert: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickAppAlert() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_alert),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "앱 알림 선택 버튼",
            modifier = Modifier.size(12.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(gray50)
        )
    }
}

@Composable
private fun RecordsAlertComponent(
    modifier: Modifier = Modifier,
    onClickRecordsAlert: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickRecordsAlert() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.records_alert),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "앱 알림 선택 버튼",
            modifier = Modifier.size(12.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(gray50)
        )
    }
}


@Preview
@Composable
private fun AlertSettingComponentPreview() {
    SeeDayTheme {
        AlertSettingComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            onClickAppAlert = {},
            onClickRecordsAlert = {}
        )
    }
}
