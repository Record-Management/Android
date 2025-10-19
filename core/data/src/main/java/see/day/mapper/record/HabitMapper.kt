package see.day.mapper.record

import android.annotation.SuppressLint
import see.day.model.record.habit.HabitRecordInput
import see.day.network.dto.record.habit.HabitRecordInputRequest

@SuppressLint("DefaultLocale")
fun HabitRecordInput.toDto() : HabitRecordInputRequest {
    return HabitRecordInputRequest(
        habitType = habitType.name,
        notificationEnabled = notificationEnabled,
        notificationTime = if(notificationEnabled) String.format("%02d:%02d", notificationHour, notificationMinute) else null,
        memo = memo.ifBlank { null },
        recordDate = recordDate
    )
}
