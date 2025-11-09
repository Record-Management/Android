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
import see.day.goal.navigation.goalNavigation
import see.day.goal.navigation.navigateCurrentGoal
import see.day.habit.navigation.habitNavigation
import see.day.habit.navigation.navigateHabitWrite
import see.day.home.navigation.homeNavigation
import see.day.home.navigation.navigateBackToHome
import see.day.setting.navigation.navigateSetting
import see.day.setting.navigation.settingNavigation
import see.day.main.viewmodel.MainViewModel
import see.day.model.navigation.AppStartState
import see.day.notification.navigation.navigateNotificationHistory
import see.day.notification.navigation.notificationNavigation
import see.day.onboarding.navigation.onboardingNavigation
import see.day.setting.navigation.navigateSettingGoalNotification
import see.day.setting.navigation.navigateSettingRecordNotification

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
                onClickSetting = navigationState.navController::navigateSetting,
                onClickNotification = navigationState.navController::navigateNotificationHistory,
                onGoCurrentGoal = navigationState.navController::navigateCurrentGoal
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
            settingNavigation(
                onBack = navigationState.navController::popBackStack,
                onGoSettingGoalNotification = navigationState.navController::navigateSettingGoalNotification,
                onGoSettingRecordNotification = navigationState.navController::navigateSettingRecordNotification
            )
            notificationNavigation(
                onBack = navigationState.navController::popBackStack,
                onClickAddRecord = navigationState::navigateAddRecord
            )
            goalNavigation(
                onBack = navigationState.navController::popBackStack
            )
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
