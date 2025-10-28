package see.day.network.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class UpdatedCountResponse(
    val updatedCount: Int
)
