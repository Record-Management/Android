package see.day.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.onboarding.screen.OnboardingCompleteScreenRoot
import see.day.onboarding.screen.OnboardingScreenRoot

const val ONBOARDING_ROUTE = "ONBOARDING"

const val ONBOARDING_COMPLETE_ROUTE = "ONBOARDING_COMPLETE"

fun NavController.navigateOnboarding(navOptions: NavOptions? = null) {
    navigate(ONBOARDING_ROUTE, navOptions)
}

fun NavController.navigateOnboardingComplete(navOptions: NavOptions? = null) {
    navigate(ONBOARDING_COMPLETE_ROUTE, navOptions)
}

fun NavGraphBuilder.onboardingNavigation(onBack: () -> Unit, onGoOnboardingComplete: () -> Unit, onGoHome: () -> Unit) {
    composable(ONBOARDING_ROUTE) {
        OnboardingScreenRoot(
            onBack = onBack,
            onGoOnboardingComplete = onGoOnboardingComplete
        )
    }
    composable(ONBOARDING_COMPLETE_ROUTE) {
        OnboardingCompleteScreenRoot(
            onGoHome = onGoHome
        )
    }
}
