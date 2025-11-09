package see.day.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray30
import see.day.setting.R
import see.day.setting.component.NotificationSwitch
import see.day.setting.state.record.RecordNotificationUiEffect
import see.day.setting.state.record.RecordNotificationUiEvent
import see.day.setting.state.record.RecordNotificationUiState
import see.day.setting.util.isNotificationPermissionGranted
import see.day.setting.util.openAppSettings
import see.day.setting.viewModel.RecordNotificationViewModel
import see.day.ui.card.ActionBanner
import see.day.ui.topbar.CommonAppBar

@Composable
internal fun SettingRecordNotificationScreenRoot(
    viewModel: RecordNotificationViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                RecordNotificationUiEffect.OnGoBack -> {
                    onBack()
                }
            }
        }
    }

    SettingRecordNotificationScreen(
        uiState = uiState,
        uiEvent = viewModel::onEvent
    )

}

@Composable
internal fun SettingRecordNotificationScreen(
    modifier: Modifier = Modifier,
    uiState: RecordNotificationUiState,
    uiEvent: (RecordNotificationUiEvent) -> Unit,
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

    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            CommonAppBar(
                modifier = modifier,
                title = R.string.blank_string,
                onClickBackButton = {
                    uiEvent(RecordNotificationUiEvent.OnGoBack)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (!hasPermission) {
                ActionBanner(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = { openAppSettings(context) },
                    title = R.string.system_notification_banner_title,
                    body = R.string.system_notification_banner_body
                )
            }
            NotificationSwitch(
                modifier = Modifier.padding(top = if (hasPermission) 10.dp else 24.dp),
                title = R.string.record_all_notification,
                body = R.string.blank_string,
                checked = uiState.isAllNotificationEnabled(),
                isAllChecked = uiState.isAllNotificationEnabled(),
                onCheckedChanged = { currentChecked ->
                    uiEvent(
                        RecordNotificationUiEvent.OnChangedRecordNotification(
                            dailyRecordNotificationEnabled = currentChecked,
                            exerciseRecordNotificationEnabled = currentChecked,
                            habitRecordNotificationEnabled = currentChecked
                        )
                    )
                }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(gray30)
                    .padding(top = 24.dp)
            )

            NotificationSwitch(
                modifier = Modifier.padding(top = 24.dp),
                title = R.string.daily_record_notification,
                body = R.string.record_notification,
                checked = uiState.dailyRecordNotificationEnabled,
                isAllChecked = uiState.isAllNotificationEnabled(),
                onCheckedChanged = { currentChecked ->
                    uiEvent(
                        RecordNotificationUiEvent.OnChangedRecordNotification(
                            dailyRecordNotificationEnabled = currentChecked,
                        )
                    )
                }
            )

            NotificationSwitch(
                modifier = Modifier.padding(top = 24.dp),
                title = R.string.exercise_record_notification,
                body = R.string.record_notification,
                checked = uiState.exerciseRecordNotificationEnabled,
                isAllChecked = uiState.isAllNotificationEnabled(),
                onCheckedChanged = { currentChecked ->
                    uiEvent(
                        RecordNotificationUiEvent.OnChangedRecordNotification(
                            exerciseRecordNotificationEnabled = currentChecked,
                        )
                    )
                }
            )

            NotificationSwitch(
                modifier = Modifier.padding(top = 24.dp),
                title = R.string.habit_record_notification,
                body = R.string.habit_time_notification,
                checked = uiState.habitRecordNotificationEnabled,
                isAllChecked = uiState.isAllNotificationEnabled(),
                onCheckedChanged = { currentChecked ->
                    uiEvent(
                        RecordNotificationUiEvent.OnChangedRecordNotification(
                            habitRecordNotificationEnabled = currentChecked,
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun SettingRecordNotificationScreenPreview() {
    SeeDayTheme {
        SettingRecordNotificationScreen(
            uiState = RecordNotificationUiState.init,
            uiEvent = {}
        )
    }
}
