package see.day.model.schedule

import java.time.LocalDate

data class ScheduleInput(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val notificationType: AlertTime,
    val notificationCustomHours: Int?,
    val notificationCustomMinutes: Int?,
    val repeatType: RepeatTime,
    val repeatEndsOn: LocalDate?,
    val location: String,
    val color: SchedulePaletteColor,
    val memo: String,
)
