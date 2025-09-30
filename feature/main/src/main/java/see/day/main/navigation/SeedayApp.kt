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
import see.day.daily.navigation.dailyNavigation
import see.day.daily.navigation.navigateDailyWrite
import see.day.home.navigation.homeNavigation
import see.day.home.navigation.navigateBackToHome
import see.day.main.navigation.graph.exerciseNavigation
import see.day.main.navigation.graph.habitNavigation
import see.day.main.navigation.graph.scheduleNavigation
import see.day.onboarding.navigation.onboardingNavigation

@Composable
fun SeedayApp(navigationState: NavigationState = rememberNavigationState(), appStartDestination: Any) {
    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(navigationState.navController, startDestination = appStartDestination) {
            loginNavigation(
                onGoOnboarding = navigationState::navigateOnboarding,
                onGoHome = navigationState::navigateHome
            )
            onboardingNavigation(
                onBack = navigationState.navController::popBackStack,
                onGoOnboardingComplete = navigationState::navigateOnboardingComplete,
                onGoHome = navigationState::navigateHome
            )
            homeNavigation(
                onClickAddRecord = navigationState::navigateAddRecord,
                onClickDetailRecord = navigationState::navigateDetailRecord
            )
            dailyNavigation(
                onClickBackButton = navigationState.navController::popBackStack,
                onClickChangeRecordType = navigationState::navigateAddRecord,
                onClickEmotion = navigationState.navController::navigateDailyWrite,
                onClickPopHome = navigationState.navController::navigateBackToHome
            )
            exerciseNavigation(
                onClickChangeRecordType = navigationState::navigateAddRecord
            )
            habitNavigation(
                onClickChangeRecordType = navigationState::navigateAddRecord
            )
            scheduleNavigation(
                onClickChangeRecordType = navigationState::navigateAddRecord
            )
        }
    }
}

@Composable
fun rememberNavigationState(navController: NavHostController = rememberNavController()): NavigationState {
    return remember(navController) {
        NavigationState(navController)
    }
}
