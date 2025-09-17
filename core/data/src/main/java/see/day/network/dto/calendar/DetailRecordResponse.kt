package see.day.network.dto.calendar

import kotlinx.serialization.Serializable

@Serializable
data class DailyDetailRecordResponse(
    val date: String,
    val records: List<DetailRecordResponse>
)

@Serializable
data class DetailRecordResponse(
    val id: String,
    val type: String,
    val emotion: String,
    val content: String,
    val imageUrls: List<String>,
    val recordDate: String,
    val recordTime: String,
    val createdAt: String,
    val updatedAt: String
)
