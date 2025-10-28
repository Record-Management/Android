package see.day.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingCompleteRequest(
    val nickname: String,
    val mainRecordType: String,
    val birthDate: String,
    val goalDays: Int
)
