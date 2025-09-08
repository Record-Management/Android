package see.day.onboarding.state.onboarding

import see.day.model.record.RecordType
import see.day.onboarding.state.OnboardingScreenState

data class OnboardingUiState(
    val onboardingScreenState: OnboardingScreenState,
    val mainRecordType: RecordType?,
    val nickname: String,
    val birthDate: String,
    val goalDays: Int,
    val notificationEnabled: Boolean
) {
    companion object {
        val init = OnboardingUiState(
            onboardingScreenState = OnboardingScreenState.RECORD,
            mainRecordType = null,
            nickname = "",
            birthDate = "",
            goalDays = 0,
            notificationEnabled = false
        )
    }
}
