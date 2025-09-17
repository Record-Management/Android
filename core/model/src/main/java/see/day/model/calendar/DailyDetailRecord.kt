package see.day.model.calendar

import see.day.model.record.RecordType

data class DailyDetailRecord(
    val date: String,
    val records: List<DetailRecord>
)

data class DetailRecord(
    val id: String,
    val type: RecordType,
    val emotion: String,
    val content: String,
    val imageUrls: List<String>,
    val recordDate: String,
    val recordTime: String,
    val createdAt: String,
    val updatedAt: String
)
