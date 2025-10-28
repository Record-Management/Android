package see.day.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoResponse(
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)
