package see.day.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.home.screen.HomeScreenRoot
import see.day.model.record.RecordType
import see.day.navigation.home.Home


fun NavController.navigateHome(navOptions: NavOptions? = null) {
    navigate(Home, navOptions)
}

fun NavController.navigateBackToHome(isUpdated: Boolean = false) {
    getBackStackEntry<Home>()
        .savedStateHandle["record_updated"] = isUpdated
    popBackStack(Home, inclusive = false)
}

fun NavGraphBuilder.homeNavigation(
    onClickAddRecord: (RecordType) -> Unit,
    onClickDetailRecord: (RecordType, String) -> Unit,
    onClickSetting: () -> Unit,
    onClickNotification: () -> Unit,
    onGoCurrentGoal: () -> Unit,
    onGoSetNewGoal: () -> Unit,
) {
    composable<Home> { navBackStack ->
        val isUpdated = navBackStack.savedStateHandle.get<Boolean>("record_updated") ?: false
        if (isUpdated) {
            navBackStack.savedStateHandle.remove<Boolean>("record_updated")
        }
        HomeScreenRoot(
            isRefresh = isUpdated,
            onClickAddRecord = onClickAddRecord,
            onClickDetailRecord = onClickDetailRecord,
            onClickSetting = onClickSetting,
            onClickNotification = onClickNotification,
            onGoCurrentGoal = onGoCurrentGoal,
            onGoSetNewGoal = onGoSetNewGoal
        )
    }
}
