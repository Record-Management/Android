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

fun NavController.navigateBackToHome() {
    popBackStack(Home, inclusive = false)
}

fun NavGraphBuilder.homeNavigation(onClickAddRecord: (RecordType) -> Unit) {
    composable<Home> {
        HomeScreenRoot(
            onClickAddRecord = onClickAddRecord
        )
    }
}
