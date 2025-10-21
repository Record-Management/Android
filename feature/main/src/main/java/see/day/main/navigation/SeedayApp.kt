package see.day.main.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import record.daily.login.navigation.loginNavigation
import see.day.daily.navigation.dailyNavigation
import see.day.daily.navigation.navigateDailyWrite
import see.day.exercise.navigation.exerciseNavigation
import see.day.exercise.navigation.navigateExerciseWrite
import see.day.habit.navigation.habitNavigation
import see.day.habit.navigation.navigateHabitWrite
import see.day.home.navigation.homeNavigation
import see.day.home.navigation.navigateBackToHome
import see.day.main.setting.navigateSetting
import see.day.main.setting.settingNavigation
import see.day.main.viewmodel.MainViewModel
import see.day.model.navigation.AppStartState
import see.day.onboarding.navigation.onboardingNavigation

@Composable
fun SeedayApp(navigationState: NavigationState = rememberNavigationState(), viewModel: MainViewModel, appStartDestination: Any) {

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
                onClickDetailRecord = navigationState::navigateDetailRecord,
                onClickSetting = navigationState.navController::navigateSetting
            )
            dailyNavigation(
                onClickBackButton = navigationState.navController::popBackStack,
                onClickChangeRecordType = navigationState::navigateAddRecord,
                onClickEmotion = navigationState.navController::navigateDailyWrite,
                onClickPopHome = navigationState.navController::navigateBackToHome
            )
            exerciseNavigation(
                onClickChangeRecordType = navigationState::navigateAddRecord,
                onBack = navigationState.navController::popBackStack,
                onClickExerciseType = navigationState.navController::navigateExerciseWrite,
                onClickPopHome = navigationState.navController::navigateBackToHome
            )
            habitNavigation(
                onClickChangeRecordType = navigationState::navigateAddRecord,
                onBack = navigationState.navController::popBackStack,
                onClickHabitType = navigationState.navController::navigateHabitWrite,
                onClickPopHome = navigationState.navController::navigateBackToHome
            )
//            scheduleNavigation(
//                onClickChangeRecordType = navigationState::navigateAddRecord
//            )
            settingNavigation()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { appState ->
            if (appState == null) return@collect

            when (appState) {
                AppStartState.LOGIN -> {
                    navigationState.navigateLoginWithCleanBackStack()
                }

                AppStartState.ONBOARDING -> {
                    navigationState.navigateOnboarding()
                }

                AppStartState.HOME -> {
                    navigationState.navigateHome()
                }
            }
        }
    }
}

@Composable
fun rememberNavigationState(navController: NavHostController = rememberNavController()): NavigationState {
    return remember(navController) {
        NavigationState(navController)
    }
}
