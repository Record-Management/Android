package see.day.model.calendar

import see.day.model.record.RecordType

data class MonthlyRecord(
    val year: Int,
    val month: Int,
    val dailyRecords: List<DailyRecords>
)

data class DailyRecords(
    val date: String,
    val records: List<DailyRecord>
)

data class DailyRecord(
    val id: String,
    val type: RecordType,
    val emotion: String
)
