package see.day.main.test

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_ROUTE = "HOME"

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeNavigation() {
    composable(HOME_ROUTE) {
        Column {
            Text("홈")
        }
    }
}
