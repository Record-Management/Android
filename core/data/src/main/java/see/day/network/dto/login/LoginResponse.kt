package see.day.network.dto.login

import kotlinx.serialization.Serializable
import see.day.network.dto.common.UserDto

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto,
    val isNewUser: Boolean
)
