package see.day.mapper

import see.day.model.user.OnboardingComplete
import see.day.network.dto.user.OnboardingCompleteRequest

fun OnboardingComplete.toDto(): OnboardingCompleteRequest {
    return OnboardingCompleteRequest(
        nickname = nickname,
        mainRecordType = mainRecordType.name,
        birthDate = birthDate,
        goalDays = goalDays,
        notificationEnabled = notificationEnabled
    )
}
