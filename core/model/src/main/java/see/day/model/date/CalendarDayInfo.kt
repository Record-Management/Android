package see.day.model.date

import see.day.model.calendar.MonthlyRecord
import see.day.model.record.RecordType

data class CalendarDayInfo(
    val year: Int,
    val month: Int,
    val day: Int,
    val mainRecordType: RecordType,
    val records: List<RecordType>,
    val schedules: List<String>,
) {
    companion object {
        fun of(monthlyRecord: MonthlyRecord): List<CalendarDayInfo> {
            return monthlyRecord.dailyRecords.map { dailyRecord ->
                val (year, month, day) = dailyRecord.date.split("-").map { it.toInt() }
//                val (scheduleRecords, otherRecords) = dailyRecord.records.partition { it.type == RecordType.SCHEDULE }
                val otherRecords = dailyRecord.records

                CalendarDayInfo(
                    year = year,
                    month = month,
                    day = day,
                    mainRecordType = dailyRecord.mainRecordType,
                    records = otherRecords.map { it.type },
                    schedules = listOf()
                )
            }
        }
    }
}
