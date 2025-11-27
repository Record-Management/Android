package record.daily.login.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
import record.daily.login.R
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray60

@Composable
internal fun PermissionCard(
    modifier: Modifier = Modifier,
    permission: Permissions
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(60.dp).clip(CircleShape).background(gray20),
        ) {
            Icon(
                painter = painterResource(permission.icon),
                contentDescription = stringResource(permission.title),
                modifier = Modifier.size(26.dp).align(Alignment.Center),
                tint = Color.Unspecified
            )
        }

        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(
                text = stringResource(permission.title) + if (!permission.isRequired) " " + stringResource(R.string.optional) else "",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(permission.body),
                style = MaterialTheme.typography.labelMedium.copy(color = gray60)
            )
        }
    }
}

enum class Permissions(val isRequired: Boolean, @StringRes val title: Int, @StringRes val body: Int, @DrawableRes val icon: Int) {
    Storage(isRequired = false, title = R.string.storage_title, body = R.string.storage_body, R.drawable.ic_storage),
    Photo(isRequired = false, title = R.string.photo_title, body = R.string.photo_body, R.drawable.ic_photo),
    Notification(isRequired = false, title = R.string.notification_title, body = R.string.notification_body, R.drawable.ic_notification)
}

@Preview
@Composable
private fun PermissionCardsPreview() {
    SeeDayTheme {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Permissions.entries.forEach { permission ->
                PermissionCard(
                    permission = permission
                )
            }
        }
    }
}
@Preview
@Composable
private fun PermissionCardPreview() {
    SeeDayTheme {
        PermissionCard(
            modifier = Modifier.padding(horizontal = 16.dp),
            permission = Permissions.Storage
        )
    }
}
