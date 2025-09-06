package see.day.network.dto.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import see.day.network.dto.common.UserDto

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto,
    @SerialName("newUser") val isNewUser: Boolean
) {
    fun isOnboardingComplete(): Boolean {
        return !isNewUser && user.onboardingCompleted
    }
}
