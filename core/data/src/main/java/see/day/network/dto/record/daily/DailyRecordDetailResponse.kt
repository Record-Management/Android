package see.day.network.dto.record.daily

import kotlinx.serialization.Serializable
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class DailyRecordDetailResponse(
    val id: String,
    val type: String,
    val emotion: String,
    val content: String,
    val imageUrls: List<String>,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recordDate: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recordTime: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val createdAt: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val updatedAt: String
)
