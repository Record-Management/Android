package see.day.setting.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import see.day.designsystem.theme.SeeDayTheme
import see.day.setting.R
import see.day.setting.component.NotificationSwitch
import see.day.setting.component.SystemNotificationCard
import see.day.setting.util.isNotificationPermissionGranted
import see.day.ui.topbar.CommonAppBar

@Composable
internal fun SettingGoalNotificationScreenRoot(
    onBack: () -> Unit
) {
    SettingGoalNotificationScreen(
        onBack = onBack
    )
}

@Composable
internal fun SettingGoalNotificationScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasPermission by remember {
        mutableStateOf(isNotificationPermissionGranted(context))
    }

    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow
        .collectAsState()

    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            hasPermission = isNotificationPermissionGranted(context)
        }
    }

    val (checked, onCheckedChanged) = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            CommonAppBar(
                modifier = modifier,
                title = R.string.black_string,
                onClickBackButton = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding).padding(horizontal = 16.dp)
        ) {
            if(!hasPermission) {
                SystemNotificationCard(
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            NotificationSwitch(
                modifier = Modifier.padding(top = 10.dp),
                title = R.string.goal_notification_title,
                body = R.string.goal_notification_body,
                checked = checked,
                isAllChecked = checked,
                onCheckedChanged = onCheckedChanged
            )
        }
    }
}

@Preview
@Composable
private fun SettingGoalNotificationScreenPreview() {
    SeeDayTheme {
        SettingGoalNotificationScreen(
            onBack = {}
        )
    }
}
