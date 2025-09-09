package see.day.onboarding.state.onboarding

import see.day.model.record.RecordType

sealed interface OnboardingUiEvent {
    data class SetRecordType(val recordType: RecordType) : OnboardingUiEvent
    data class EnterNickname(val name: String) : OnboardingUiEvent
    data class EnterBirthDay(val birthDay: String) : OnboardingUiEvent
    data class EnterGoal(val goalDay: Int) : OnboardingUiEvent
    data class CheckNotification(val notificationEnabled: Boolean) : OnboardingUiEvent
    data object FinishOnboarding : OnboardingUiEvent
    data object OnBack : OnboardingUiEvent
}
