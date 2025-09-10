package see.day.main.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import record.daily.login.navigation.loginNavigation
import see.day.main.navigation.graph.mainNavigation
import see.day.main.test.homeNavigation
import see.day.onboarding.navigation.onboardingNavigation

@Composable
fun SeedayApp(navigationState: NavigationState = rememberNavigationState(), appStartDestination: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(navigationState.navController, startDestination = appStartDestination) {
            mainNavigation(
                onClickLogin = navigationState::navigateLogin
            )
            loginNavigation(
                onGoOnboarding = navigationState::navigateOnboarding,
                onGoHome = navigationState::navigateHome
            )
            onboardingNavigation(
                onBack = navigationState.navController::popBackStack,
                onGoOnboardingComplete = navigationState::navigateOnboardingComplete,
                onGoHome = navigationState::navigateHome
            )
            homeNavigation()
        }
    }
}

@Composable
fun rememberNavigationState(navController: NavHostController = rememberNavController()): NavigationState {
    return remember(navController) {
        NavigationState(navController)
    }
}
