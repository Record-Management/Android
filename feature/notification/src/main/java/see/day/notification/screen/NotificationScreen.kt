package see.day.notification.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.notification.NotificationType
import see.day.model.record.RecordType
import see.day.notification.R
import see.day.notification.component.HistoryCard
import see.day.notification.component.HistoryEmptyComponent
import see.day.notification.state.NotificationHistoryUiModel
import see.day.notification.state.NotificationUiEffect
import see.day.notification.state.NotificationUiEvent
import see.day.notification.state.NotificationUiState
import see.day.notification.util.TimeFormatUtil
import see.day.notification.viewModel.NotificationViewModel
import see.day.ui.card.ActionBanner
import see.day.ui.topbar.CommonAppBar

@Composable
internal fun NotificationScreenRoute(
    viewModel: NotificationViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateToWriteRecord: (RecordType, Boolean) -> Unit,
    onNavigateToResetGoal: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                NotificationUiEffect.NavigateToBackStack -> {
                    onBack()
                }

                is NotificationUiEffect.NavigateToWriteRecord -> {
                    onNavigateToWriteRecord(effect.recordType, true)
                }

                NotificationUiEffect.NavigateToResetGoal -> {
                    onNavigateToResetGoal()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    NotificationScreen(
        uiState = uiState.value,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun NotificationScreen(
    modifier: Modifier = Modifier,
    uiState: NotificationUiState,
    onAction: (NotificationUiEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .systemBarsPadding(),
        topBar = {
            CommonAppBar(
                title = R.string.notification_title,
                onClickBackButton = { onAction(NotificationUiEvent.OnClickBack) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.notificationHistories.isEmpty()) {
                HistoryEmptyComponent(
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(uiState.notificationHistories) { history ->
                        if (history.isVisible()) {
                            HistoryCard(
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                                notificationType = history.notificationType,
                                title = history.title,
                                message = history.message,
                                relativeTime = history.relativeTime,
                                isChecked = history.isChecked,
                                onClickCard = { type, time ->
                                    if (!uiState.hasNoGoal) {
                                        onAction(NotificationUiEvent.OnClickItem(type, time))
                                    }
                                },
                            )
                        }
                    }
                }
            }
            if (uiState.hasNoGoal) {
                ActionBanner(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp),
                    onClick = {
                        onAction(NotificationUiEvent.OnClickResetGoalBanner)
                    },
                    title = see.day.ui.R.string.current_goal_banner_title,
                    body = see.day.ui.R.string.current_goal_banner_body,
                )
            }
        }
    }
}


@Preview
@Composable
private fun NotificationScreenPreview() {
    SeeDayTheme {
        NotificationScreen(
            uiState = NotificationUiState.init,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun NotificationScreenHistoriesPreview() {
    SeeDayTheme {
        NotificationScreen(
            uiState = NotificationUiState.init.copy(notificationHistories = getSampleNotificationHistory(3)),
            onAction = {}
        )
    }
}

private fun getSampleNotificationHistory(count: Int): List<NotificationHistoryUiModel> {
    return (0 until count).map { num ->
        NotificationHistoryUiModel(
            notificationType = NotificationType.entries[num % 3],
            title = "asdasd",
            message = "asdasd",
            relativeTime = TimeFormatUtil.getRelativeTimeString(TimeFormatUtil.daysBefore(num.toLong())),
            isChecked = num % 2 == 0
        )
    }
}
