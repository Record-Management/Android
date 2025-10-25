package see.day.notification.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme

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
    Column {
        Text("Notification")
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
