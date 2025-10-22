package see.day.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileChangedInputRequest(
    val nickname: String?,
    val birthDate: String?
)
