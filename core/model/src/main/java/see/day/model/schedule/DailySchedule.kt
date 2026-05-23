package see.day.model.schedule

import java.time.LocalDate

data class DailySchedule(
    val scheduleId: String,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val color: SchedulePaletteColor,
    val memo: String,
)
