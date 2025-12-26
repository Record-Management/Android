package see.day.analytics

import com.google.firebase.analytics.FirebaseAnalytics

sealed class AnalyticsEvent(val name: String) {
    data object Login: AnalyticsEvent(FirebaseAnalytics.Event.LOGIN)
    data object SignUp: AnalyticsEvent(FirebaseAnalytics.Event.SIGN_UP)
    data object OnboardingComplete: AnalyticsEvent("onboarding_complete")
    data object Logout: AnalyticsEvent("logout")
    data object Withdrawal: AnalyticsEvent("withdrawal")
}
