package see.day.model.date

import see.day.model.record.RecordType

data class CalendarDayInfo(
    val year: Int,
    val month: Int,
    val day: Int,
    val records: List<RecordType>,
    val schedules: List<String>,
)
