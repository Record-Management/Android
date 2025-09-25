package see.day.network.dto.photo

import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    val fileUrls: List<String>
)
