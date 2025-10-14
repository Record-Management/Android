package see.day.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserRequest(
    val reason: String
)
