package see.day.network.dto.schedule

import kotlinx.serialization.Serializable
import see.day.model.schedule.SchedulePaletteColor
import see.day.network.decoder.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class DailyScheduleResponse(
    val scheduleId: String,
    val title: String,
    @Serializable(with = LocalDateSerializer::class)
    val startDate: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val endDate: LocalDate,
    val color: SchedulePaletteColor,
    val memo: String? = null,
)
