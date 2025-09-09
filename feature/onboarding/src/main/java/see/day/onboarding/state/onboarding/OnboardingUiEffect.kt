package see.day.onboarding.state.onboarding

sealed interface OnboardingUiEffect {
    data object FinishApp : OnboardingUiEffect
    data object GoHome : OnboardingUiEffect
}
