package record.daily.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import record.daily.login.screen.LoginScreenRoot

const val LOGIN_ROUTE = "LOGIN"

fun NavController.navigateLogin(navOptions: NavOptions? = null) {
    navigate(LOGIN_ROUTE, navOptions)
}

fun NavGraphBuilder.loginNavigation(onGoHome: () -> Unit, onGoOnboarding: () -> Unit) {
    composable(LOGIN_ROUTE) {
        LoginScreenRoot(
            onGoOnboarding = onGoOnboarding,
            onGoHome = onGoHome
        )
    }
}
