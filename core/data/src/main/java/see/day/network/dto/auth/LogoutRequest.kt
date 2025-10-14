package see.day.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequest(
    val refreshToken: String,
    val allDevices: Boolean
)
