package see.day.network.dto.record.daily

import kotlinx.serialization.Serializable

@Serializable
data class DailyRecordInputRequest(
    val content: String,
    val emotion: String,
    val recordDate: String,
    val recordTime: String,
    val imageUrls: List<String>
)
