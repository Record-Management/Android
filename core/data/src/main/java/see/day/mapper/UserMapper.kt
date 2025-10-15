package see.day.mapper

import see.day.model.login.SocialType
import see.day.model.record.RecordType
import see.day.model.user.OnboardingComplete
import see.day.model.user.User
import see.day.network.dto.user.FullUserResponse
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

fun FullUserResponse.toModel(): User {
    return User(
        id = id,
        name = name,
        nickname = nickname,
        email = email ?: "",
        socialType = SocialType.valueOf(socialType),
        mainRecordType = RecordType.valueOf(mainRecordType),
        birthDate = birthDate,
        goalDays = goalDays,
        notificationEnabled = notificationEnabled,
        onboardingCompleted = onboardingCompleted,
        createdAt = createdAt
    )
}
