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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.setting.R
import see.day.setting.component.NotificationSwitch
import see.day.setting.state.goal.GoalNotificationUiEffect
import see.day.setting.state.goal.GoalNotificationUiEvent
import see.day.setting.state.goal.GoalNotificationUiState
import see.day.setting.util.isNotificationPermissionGranted
import see.day.setting.util.openAppSettings
import see.day.setting.viewModel.GoalNotificationViewModel
import see.day.ui.card.ActionBanner
import see.day.ui.topbar.CommonAppBar

@Composable
internal fun SettingGoalNotificationScreenRoot(
    viewModel: GoalNotificationViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                GoalNotificationUiEffect.OnGoBack -> {
                    onBack()
                }
            }
        }
    }
    SettingGoalNotificationScreen(
        uiState = uiState,
        uiEvent = viewModel::onEvent
    )
}

@Composable
internal fun SettingGoalNotificationScreen(
    modifier: Modifier = Modifier,
    uiState: GoalNotificationUiState,
    uiEvent: (GoalNotificationUiEvent) -> Unit
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
                    uiEvent(GoalNotificationUiEvent.OnGoBack)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding).padding(horizontal = 16.dp)
        ) {
            if(!hasPermission) {
                ActionBanner(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = { openAppSettings(context) },
                    title = R.string.system_notification_banner_title,
                    body = R.string.system_notification_banner_body
                )
            }
            NotificationSwitch(
                modifier = Modifier.padding(top = if(hasPermission) 10.dp else 24.dp),
                title = R.string.goal_notification_title,
                body = R.string.goal_notification_body,
                checked = uiState.goalNotificationEnabled,
                isAllChecked = uiState.goalNotificationEnabled,
                onCheckedChanged = { currentChecked ->
                    uiEvent(GoalNotificationUiEvent.OnChangedGoalNotification(currentChecked))
                }
            )
        }
    }
}

@Preview
@Composable
private fun SettingGoalNotificationScreenPreview() {
    SeeDayTheme {
        SettingGoalNotificationScreen(
            uiState = GoalNotificationUiState.init,
            uiEvent = {}
        )
    }
}
