package see.day.model.schedule

import java.time.LocalDate

data class ScheduleDetail(
    val scheduleRecordId: String,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val alertType: AlertTime,
    val notificationCustomHours: Int = 9,
    val notificationCustomMinutes: Int = 0,
    val repeatType: RepeatTime,
    val repeatEndsOn: LocalDate?,
    val location: String,
    val color: SchedulePaletteColor,
    val memo: String
)
