package see.day.network.dto.schedule

import kotlinx.serialization.Serializable
import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.model.schedule.SchedulePaletteColor
import see.day.network.decoder.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class ScheduleResponse(
    val scheduleRecordId: String,
    val title: String,
    @Serializable(with = LocalDateSerializer::class)
    val startDate: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val endDate: LocalDate,
    val notificationType: AlertTime,
    val notificationCustomHours: Int? = null,
    val notificationCustomMinutes: Int? = null,
    val repeatType: RepeatTime,
    @Serializable(with = LocalDateSerializer::class)
    val repeatEndsOn: LocalDate? = null,
    val location: String? = null,
    val color: SchedulePaletteColor,
    val memo: String? = null,
)
