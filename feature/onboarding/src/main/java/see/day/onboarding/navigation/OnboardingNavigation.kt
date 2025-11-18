package see.day.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import see.day.navigation.onboarding.CompleteType
import see.day.navigation.onboarding.OnboardingRoute
import see.day.onboarding.screen.OnboardingCompleteScreenRoot
import see.day.onboarding.screen.OnboardingScreenRoot


fun NavController.navigateOnboarding(navOptions: NavOptions? = null) {
    navigate(OnboardingRoute.Onboarding, navOptions)
}

fun NavController.navigateOnboardingComplete(completeType: CompleteType, navOptions: NavOptions? = null) {
    navigate(OnboardingRoute.OnboardingComplete(completeType), navOptions)
}

fun NavGraphBuilder.onboardingNavigation(onBack: () -> Unit, onGoOnboardingComplete: (CompleteType) -> Unit, onGoHome: () -> Unit) {
    composable<OnboardingRoute.Onboarding> {
        OnboardingScreenRoot(
            onBack = onBack,
            onGoOnboardingComplete = onGoOnboardingComplete
        )
    }
    composable<OnboardingRoute.OnboardingComplete> { navBackStackEntry ->
        val type = navBackStackEntry.toRoute<OnboardingRoute.OnboardingComplete>().completeType

        OnboardingCompleteScreenRoot(
            completeType = type,
            onGoHome = onGoHome
        )
    }
}
