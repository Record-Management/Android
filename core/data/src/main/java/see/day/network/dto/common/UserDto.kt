package see.day.network.dto.common

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String?,
    val name: String?,
    val email: String?,
    val socialType: String,
    val createdAt: String,
    val onboardingCompleted: Boolean
)
