package see.day.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshUserResponse(
    val id: String?,
    val name: String?,
    val email: String?,
    val onboardingCompleted: Boolean
)
