package see.day.setting.component

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray90
import see.day.designsystem.theme.onError
import see.day.setting.R
import see.day.ui.dialog.ConfirmDialog

@Composable
internal fun ExtSettingComponent(
    modifier: Modifier = Modifier,
    onClickInquiry: () -> Unit,
    onClickLogout: () -> Unit,
    onClickWithdrawal: () -> Unit
) {
    val context = LocalContext.current

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
            PolicyComponent(Modifier, context)
            InquiryComponent(Modifier, onClickInquiry)
            LogoutComponent(Modifier, onClickLogout)
            WithdrawalComponent(Modifier, onClickWithdrawal)
        }
    }
}

@Composable
private fun PolicyComponent(
    modifier: Modifier = Modifier,
    context: Context,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, "https://placid-aurora-3ad.notion.site/2b54e8ebd8b080c1a8bdd9267b94dc3e?source=copy_link".toUri())
                context.startActivity(intent)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.policy),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "약관 및 정책 버튼",
            modifier = Modifier.size(12.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(gray50)
        )
    }
}

@Composable
private fun InquiryComponent(
    modifier: Modifier = Modifier,
    onClickInquiry: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickInquiry() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.inquiry),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "문의하기 버튼",
            modifier = Modifier.size(12.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(gray50)
        )
    }
}

@Composable
private fun LogoutComponent(
    modifier: Modifier = Modifier,
    onClickLogout: () -> Unit
) {
    var openLogoutDialog by remember { mutableStateOf(false) }

    if (openLogoutDialog) {
        ConfirmDialog(
            title = see.day.ui.R.string.logout_title,
            body = see.day.ui.R.string.logout_body,
            cancel = see.day.ui.R.string.logout_cancel,
            confirm = see.day.ui.R.string.logout_confirm,
            onDismiss = { openLogoutDialog = false },
            onClickConfirmButton = {
                onClickLogout()
                openLogoutDialog = false
            }
        )
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { openLogoutDialog = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.logout),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
    }
}

@Composable
private fun WithdrawalComponent(
    modifier: Modifier = Modifier,
    onClickWithdrawal: () -> Unit
) {
    var openWithdrawalDialog by remember { mutableStateOf(false) }

    if (openWithdrawalDialog) {
        ConfirmDialog(
            title = see.day.ui.R.string.withdrawal_title,
            body = see.day.ui.R.string.withdrawal_body,
            cancel = see.day.ui.R.string.withdrawal_cancel,
            confirm = see.day.ui.R.string.withdrawal_confirm,
            onDismiss = { openWithdrawalDialog = false },
            onClickConfirmButton = {
                onClickWithdrawal()
                openWithdrawalDialog = false
            },
            confirmButtonColor = onError
        )
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { openWithdrawalDialog = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.withdrawal),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
    }
}

@Preview
@Composable
private fun ExtSettingComponentPreview() {
    SeeDayTheme {
        ExtSettingComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            onClickInquiry = {},
            onClickLogout = {},
            onClickWithdrawal = {}
        )
    }
}
