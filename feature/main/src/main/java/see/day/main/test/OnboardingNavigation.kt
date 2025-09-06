package see.day.main.test

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val ONBOARDING_ROUTE = "ONBOARDING"

fun NavController.navigateOnboarding(navOptions: NavOptions? = null) {
    navigate(ONBOARDING_ROUTE, navOptions)
}

fun NavGraphBuilder.onboardingNavigation() {
    composable(ONBOARDING_ROUTE) {
        Column {
            Text("온보딩")
        }
    }
}
