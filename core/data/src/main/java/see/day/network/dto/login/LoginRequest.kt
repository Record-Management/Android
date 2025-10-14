package see.day.network.dto.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val socialType: String,
    val accessToken: String
)
