package see.day.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.navigation.onboarding.OnboardingRoute
import see.day.onboarding.screen.OnboardingCompleteScreenRoot
import see.day.onboarding.screen.OnboardingScreenRoot


fun NavController.navigateOnboarding(navOptions: NavOptions? = null) {
    navigate(OnboardingRoute.Onboarding, navOptions)
}

fun NavController.navigateOnboardingComplete(navOptions: NavOptions? = null) {
    navigate(OnboardingRoute.OnboardingComplete, navOptions)
}

fun NavGraphBuilder.onboardingNavigation(onBack: () -> Unit, onGoOnboardingComplete: () -> Unit, onGoHome: () -> Unit) {
    composable<OnboardingRoute.Onboarding> {
        OnboardingScreenRoot(
            onBack = onBack,
            onGoOnboardingComplete = onGoOnboardingComplete
        )
    }
    composable<OnboardingRoute.OnboardingComplete> {
        OnboardingCompleteScreenRoot(
            onGoHome = onGoHome
        )
    }
}
