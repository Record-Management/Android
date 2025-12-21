package see.day.onboarding.state.onboarding

import see.day.model.record.RecordType

sealed interface OnboardingUiEvent {
    data object ConfirmTerms : OnboardingUiEvent
    data class SetRecordType(val recordType: RecordType) : OnboardingUiEvent
    data class EnterNickname(val name: String) : OnboardingUiEvent
    data class EnterBirthDay(val birthDay: String) : OnboardingUiEvent
    data class SetGoalDays(val goalDay: Int) : OnboardingUiEvent
    data object OnClickFinishOnboarding : OnboardingUiEvent
    data object OnClickBack : OnboardingUiEvent
}
