package see.day.onboarding.state.onboarding

import java.time.LocalDate
import see.day.model.record.RecordType
import see.day.onboarding.state.OnboardingScreenState

data class OnboardingUiState(
    val onboardingScreenState: OnboardingScreenState,
    val mainRecordType: RecordType?,
    val nickname: String,
    val birthDate: String,
    val goalDays: Int
) {
    companion object {
        val init = OnboardingUiState(
            onboardingScreenState = OnboardingScreenState.TERMS,
            mainRecordType = null,
            nickname = "",
            birthDate = todayDate(),
            goalDays = 0
        )
        private fun todayDate(): String {
            val now = LocalDate.now()
            return "%04d-%02d-%02d".format(now.year, now.monthValue, now.dayOfMonth)
        }
    }
}
