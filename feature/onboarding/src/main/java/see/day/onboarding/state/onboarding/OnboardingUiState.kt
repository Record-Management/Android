package see.day.onboarding.state.onboarding

import see.day.model.record.RecordType
import see.day.onboarding.state.OnboardingScreenState
import java.time.LocalDate

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
            birthDate = todayDate(),
            goalDays = 0,
            notificationEnabled = false
        )
        private fun todayDate() : String {
            val now = LocalDate.now()
            return "${now.year}-${now.monthValue}-${now.dayOfMonth}"
        }
    }
}
