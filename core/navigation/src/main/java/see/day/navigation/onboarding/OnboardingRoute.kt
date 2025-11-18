package see.day.navigation.onboarding

import kotlinx.serialization.Serializable

sealed interface OnboardingRoute {

    @Serializable
    data object Onboarding : OnboardingRoute

    @Serializable
    data class OnboardingComplete(val completeType: CompleteType) : OnboardingRoute
}

@Serializable
enum class CompleteType {
    ONBOARDING,
    RESET_GOAL;
}
