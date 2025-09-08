package see.day.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import record.daily.login.navigation.navigateLogin
import see.day.main.test.navigateHome
import see.day.onboarding.navigation.navigateOnboarding

class NavigationState(
    val navController: NavHostController
) {
    val currentDestination: State<NavBackStackEntry?>
        @Composable get() = navController.currentBackStackEntryAsState()

    private fun cleanBackstackNavOptions() = navOptions {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
        launchSingleTop = true
    }

    fun navigateLogin() {
        navController.navigateLogin(cleanBackstackNavOptions())
    }

    fun navigateOnboarding() {
        navController.navigateOnboarding(cleanBackstackNavOptions())
    }

    fun navigateHome() {
        navController.navigateHome(cleanBackstackNavOptions())
    }
}
