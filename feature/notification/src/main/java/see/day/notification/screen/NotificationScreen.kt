package see.day.notification.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme
import see.day.notification.R
import see.day.ui.topbar.CommonAppBar

@Composable
internal fun NotificationScreenRoute(
    onBack: () -> Unit
) {
    NotificationScreen(
        onBack = onBack
    )
}

@Composable
internal fun NotificationScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .systemBarsPadding(),
        topBar = {
            CommonAppBar(
                title = R.string.notification_title,
                onClickBackButton = onBack
            )
        }
    ) { innerPadding ->
        Column {
            Text(
                modifier = Modifier.padding(innerPadding),
                text = "Notification"
            )

        }
    }

}


@Preview
@Composable
private fun NotificationScreenPreview() {
    SeeDayTheme {
        NotificationScreen(
            onBack = {}
        )
    }
}
