package record.daily.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import record.daily.login.screen.LoginScreenRoot
import see.day.navigation.login.Login


fun NavController.navigateLogin(navOptions: NavOptions? = null) {
    navigate(Login, navOptions)
}

fun NavGraphBuilder.loginNavigation(onGoHome: () -> Unit, onGoOnboarding: () -> Unit) {
    composable<Login> {
        LoginScreenRoot(
            onGoOnboarding = onGoOnboarding,
            onGoHome = onGoHome
        )
    }
}
