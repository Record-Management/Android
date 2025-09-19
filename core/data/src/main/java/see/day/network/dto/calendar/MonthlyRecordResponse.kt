package see.day.network.dto.calendar

import kotlinx.serialization.Serializable
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class MonthlyRecordResponse(
    val year: Int,
    val month: Int,
    val monthlyRecords: List<DailyRecordsResponse>
)

@Serializable
data class DailyRecordsResponse(
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val date: String,
    val records: List<DailyRecordResponse>
)

@Serializable
data class DailyRecordResponse(
    val id: String,
    val type: String,
)

