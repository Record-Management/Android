package see.day.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val user: RefreshUserResponse,
)
