package see.day.notification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.navigation.notification.NotificationRoute
import see.day.navigation.notification.NotificationRoute.History
import see.day.notification.screen.NotificationScreenRoute

fun NavController.navigateNotificationHistory(navOptions: NavOptions? = null) {
    navigate(History, navOptions)
}

fun NavGraphBuilder.notificationNavigation(onBack: () -> Unit) {
    composable<History> {
        NotificationScreenRoute(
            onBack = onBack
        )
    }
}
