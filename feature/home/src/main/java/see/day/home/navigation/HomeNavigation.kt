package see.day.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.home.screen.HomeScreenRoot
import see.day.model.record.RecordType

const val HOME_ROUTE = "HOME"

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeNavigation(onClickAddRecord: (RecordType) -> Unit) {
    composable(HOME_ROUTE) {
        HomeScreenRoot(
            onClickAddRecord = onClickAddRecord
        )
    }
}
