package see.day.setting.component

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.setting.R

@Composable
internal fun SystemNotificationCard(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { openAppSettings(context) }
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.system_notification_description),
            style = MaterialTheme.typography.labelMedium.copy(Color.White)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(Color.White)
                .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.setting_notification),
                style = MaterialTheme.typography.displaySmall.copy(color = MaterialTheme.colorScheme.primary)
            )
            Image(
                painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                contentDescription = "선택",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}

@Preview
@Composable
private fun SystemNotificationCardPreview() {
    SeeDayTheme {
        SystemNotificationCard(
            modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)
        )
    }
}
