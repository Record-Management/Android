package see.day.mapper.schedule

import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.model.schedule.ScheduleInput
import see.day.model.time.formatter.formatIsoDate
import see.day.network.dto.schedule.ScheduleInputRequest

fun ScheduleInput.toDto(): ScheduleInputRequest {
    return ScheduleInputRequest(
        title = title,
        startDate = startDate.formatIsoDate(),
        endDate = endDate.formatIsoDate(),
        notificationType = notificationType.name,
        notificationCustomHours = if(notificationType == AlertTime.CUSTOM) notificationCustomHours else null,
        notificationCustomMinutes = if(notificationType == AlertTime.CUSTOM) notificationCustomMinutes else null,
        repeatType = repeatType.name,
        repeatEndsOn = if(repeatType != RepeatTime.NONE) repeatEndsOn?.formatIsoDate() else null,
        location = location.ifEmpty { null },
        color = color.name,
        memo = memo.ifEmpty { null },
    )
}
