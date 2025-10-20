package see.day.network.dto.record.habit

import kotlinx.serialization.Serializable

@Serializable
data class HabitRecordCompleteRequest(
    val isCompleted: Boolean
)
