package see.day.network.dto.user

import kotlinx.serialization.Serializable
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class FullUserResponse(
    val id: String,
    val name: String,
    val nickname: String,
    val email: String? = null,
    val socialType: String,
    val mainRecordType: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val birthDate: String,
    val goalDays: Int,
    val onboardingCompleted: Boolean,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val createdAt: String
)
