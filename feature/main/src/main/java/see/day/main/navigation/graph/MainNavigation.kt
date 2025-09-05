package see.day.main.navigation.graph

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import see.day.main.Greeting

const val MAIN_ROUTE = "MAIN"

fun NavGraphBuilder.mainNavigation(onClickLogin: () -> Unit) {
    composable(MAIN_ROUTE) {
        Greeting(
            "hi",
            modifier = Modifier,
            onClickLogin = onClickLogin
        )
    }
}
