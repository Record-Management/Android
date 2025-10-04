package see.day.network.dto.calendar

import kotlinx.serialization.Serializable
import see.day.network.decoder.FlexibleDateTimeArraySerializer
import see.day.network.dto.record.RecordResponse

@Serializable
data class DailyDetailRecordResponse(
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val date: String,
    val records: List<RecordResponse>
)
