package record.daily.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import record.daily.login.screen.LoginScreenRoot
import record.daily.login.screen.PermissionScreenRoot
import see.day.navigation.login.LoginRoute.Login
import see.day.navigation.login.LoginRoute.Permission


fun NavController.navigateLogin(navOptions: NavOptions? = null) {
    navigate(Login, navOptions)
}

fun NavController.navigatePermission(navOptions: NavOptions? = null) {
    navigate(Permission, navOptions)
}

fun NavGraphBuilder.loginNavigation(onGoHome: () -> Unit, onGoOnboarding: () -> Unit, onGoPermission: () -> Unit, onBack: () -> Unit) {
    composable<Login> {
        LoginScreenRoot(
            onGoOnboarding = onGoOnboarding,
            onGoHome = onGoHome,
            onGoPermission = onGoPermission
        )
    }
    composable<Permission> {
        PermissionScreenRoot(
            onBack = onBack
        )
    }
}
