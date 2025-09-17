package see.day.network.dto.calendar

import kotlinx.serialization.Serializable

@Serializable
data class MonthlyRecordResponse(
    val year: Int,
    val month: Int,
    val dailyRecords: List<DailyRecordsResponse>
)

@Serializable
data class DailyRecordsResponse(
    val date: String,
    val records: List<DailyRecordResponse>
)

@Serializable
data class DailyRecordResponse(
    val id: String,
    val type: String,
    val emotion: String
)

