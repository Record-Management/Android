package see.day.model.calendar

import see.day.model.record.RecordType
import see.day.model.schedule.SchedulePaletteColor

data class MonthlyRecord(
    val year: Int,
    val month: Int,
    val dailyRecords: List<DailyRecords>,
)

data class DailyRecords(
    val date: String,
    val mainRecordType : RecordType?,
    val records: List<DailyRecord>,
    val schedules: DailySchedule?,
)

data class DailyRecord(
    val id: String,
    val type: RecordType,
    val isCompleted: Boolean
)

data class DailySchedule(
    val title: String,
    val size: Int,
    val color: SchedulePaletteColor
)
