package see.day.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import see.day.navigation.schedule.ScheduleRoute
import see.day.navigation.schedule.ScheduleRoute.ScheduleDetail
import see.day.navigation.schedule.ScheduleRoute.ScheduleWrite
import see.day.schedule.screen.ScheduleDetailScreenRoot
import see.day.schedule.state.SchedulePostType

fun NavController.navigateSchedule(navOptions: NavOptions? = null) {
    navigate(ScheduleWrite, navOptions)
}

fun NavController.navigateScheduleDetail(recordId: String, navOptions: NavOptions? = null) {
    navigate(ScheduleDetail(recordId), navOptions)
}

fun NavGraphBuilder.scheduleNavigation(onBack: () -> Unit, onClickPopHome: (Boolean) -> Unit) {
    composable<ScheduleWrite> {
        ScheduleDetailScreenRoot(
            editType = SchedulePostType.Write,
            onClickPopHome = onClickPopHome
        )
    }
    composable<ScheduleDetail> { navBackStackEntry ->
        val recordId = navBackStackEntry.toRoute<ScheduleDetail>().recordId
        ScheduleDetailScreenRoot(
            editType = SchedulePostType.Edit(recordId),
            onClickPopHome = onClickPopHome
        )
    }
}
