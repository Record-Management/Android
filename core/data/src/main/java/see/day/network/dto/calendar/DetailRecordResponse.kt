package see.day.network.dto.calendar

import kotlinx.serialization.Serializable
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class DailyDetailRecordResponse(
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val date: String,
    val records: List<DetailRecordResponse>
)

@Serializable
data class DetailRecordResponse(
    val id: String,
    val type: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recordDate: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recordTime: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val createdAt: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val updatedAt: String,
    val imageUrls: List<String>,
    val emotion: String,
    val content: String
)
