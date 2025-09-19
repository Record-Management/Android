package see.day.network.dto.common

import kotlinx.serialization.Serializable
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class UserDto(
    val id: String?,
    val name: String?,
    val email: String?,
    val socialType: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val createdAt: String,
    val onboardingCompleted: Boolean
)
