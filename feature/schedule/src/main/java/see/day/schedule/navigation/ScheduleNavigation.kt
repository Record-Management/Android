package see.day.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.navigation.schedule.ScheduleRoute.Schedule
import see.day.schedule.screen.ScheduleDetailScreenRoot

fun NavController.navigateSchedule(navOptions: NavOptions? = null) {
    navigate(Schedule, navOptions)
}

fun NavGraphBuilder.scheduleNavigation(onBack: () -> Unit, onClickPopHome: (Boolean) -> Unit) {
    composable<Schedule> {
        ScheduleDetailScreenRoot(
            onBack = onBack,
            onClickPopHome = onClickPopHome
        )
    }
}
