package see.day.navigation.onboarding

import kotlinx.serialization.Serializable

sealed interface OnboardingRoute {

    @Serializable
    data object Onboarding : OnboardingRoute

    @Serializable
    data object OnboardingComplete : OnboardingRoute
}
