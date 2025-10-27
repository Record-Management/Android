package see.day.network

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoResponse(
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)
