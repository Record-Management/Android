package see.day.setting.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray60
import see.day.designsystem.theme.gray70
import see.day.setting.R

@Composable
internal fun NotificationSwitch(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes body: Int,
    checked: Boolean,
    isAllChecked: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.clickable { onCheckedChanged(!checked) }.then(modifier)
    ) {
        Column {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.titleSmall.copy(if(isAllChecked) gray100 else gray70)
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(body),
                style = MaterialTheme.typography.labelMedium.copy(gray60)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChanged,
            modifier = Modifier.size(24.dp).padding(end = 24.dp),
            colors = SwitchDefaults.colors().copy(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF2DC86F),
                checkedBorderColor = Color.White,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFD7D7D7),
                uncheckedBorderColor = Color.White,
            )
        )
    }
}

@Preview
@Composable
private fun NotificationGoalSwitchPreview() {
    val (checked, onChangedChecked) = remember { mutableStateOf(false) }
    SeeDayTheme {
        NotificationSwitch(
            modifier = Modifier.background(Color.White).padding(top = 10.dp, start = 16.dp, end = 16.dp),
            title = R.string.goal_notification_title,
            body = R.string.goal_notification_body,
            checked = checked,
            isAllChecked = checked,
            onCheckedChanged =onChangedChecked
        )
    }
}
