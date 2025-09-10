package see.day.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class FullUserResponse(
    val id: String,
    val name: String,
    val nickname: String,
    val email: String?,
    val socialType: String,
    val mainRecordType: String,
    val birthDate: String,
    val goalDays: Int,
    val notificationEnabled: Boolean,
    val onboardingComplete: Boolean = true,
    val createdAt: String
)
