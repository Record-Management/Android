package see.day.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import record.daily.login.navigation.navigateLogin
import see.day.daily.navigation.navigateDaily
import see.day.daily.navigation.navigateDailyDetail
import see.day.exercise.navigation.navigateExercise
import see.day.exercise.navigation.navigateExerciseDetail
import see.day.habit.navigation.navigateHabit
import see.day.habit.navigation.navigateHabitDetail
import see.day.home.navigation.navigateHome
import see.day.main.navigation.graph.navigateSchedule
import see.day.model.record.RecordType
import see.day.onboarding.navigation.navigateOnboarding
import see.day.onboarding.navigation.navigateOnboardingComplete

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

    fun navigateLoginWithCleanBackStack() {
        navController.navigateLogin(cleanBackstackNavOptions())
    }

    fun navigateOnboarding() {
        navController.navigateOnboarding(cleanBackstackNavOptions())
    }

    fun navigateHome() {
        navController.navigateHome(cleanBackstackNavOptions())
    }

    fun navigateOnboardingComplete() {
        navController.navigateOnboardingComplete(cleanBackstackNavOptions())
    }

    fun navigateAddRecord(recordType: RecordType, deleteBackStack: Boolean = false) {
        val navOptions: NavOptions? = if (deleteBackStack) {
            navOptions {
                popUpTo(navController.previousBackStackEntry?.destination?.id ?: -1)
            }
        } else {
            null
        }
        when (recordType) {
            RecordType.DAILY -> {
                navController.navigateDaily(navOptions)
            }
            RecordType.EXERCISE -> {
                navController.navigateExercise(navOptions)
            }
            RecordType.HABIT -> {
                navController.navigateHabit(navOptions)
            }
//            RecordType.SCHEDULE -> {
//                navController.navigateSchedule(navOptions)
//            }
        }
    }

    fun navigateDetailRecord(recordType: RecordType, recordId: String) {
        when(recordType) {
            RecordType.DAILY -> {
                navController.navigateDailyDetail(recordId)
            }
            RecordType.EXERCISE -> {
                navController.navigateExerciseDetail(recordId)
            }
            RecordType.HABIT -> {
                navController.navigateHabitDetail(recordId)
            }
//            RecordType.SCHEDULE -> {
//
//            }
        }
    }
}
