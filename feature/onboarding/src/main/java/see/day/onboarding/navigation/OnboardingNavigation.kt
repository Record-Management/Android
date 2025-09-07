package see.day.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.onboarding.screen.OnboardingScreenRoot

const val ONBOARDING_ROUTE = "ONBOARDING"

fun NavController.navigateOnboarding(navOptions: NavOptions? = null) {
    navigate(ONBOARDING_ROUTE, navOptions)
}

fun NavGraphBuilder.onboardingNavigation(onBack: () -> Unit, onGoHome: () -> Unit) {
    composable(ONBOARDING_ROUTE) {
        OnboardingScreenRoot(
            onBack = onBack,
            onGoHome = onGoHome
        )
    }
}
