package see.day.network.dto.record.habit

import kotlinx.serialization.Serializable

@Serializable
data class HabitRecordInputRequest(
    val habitType: String,
    val notificationEnabled: Boolean,
    val notificationTime: String?,
    val memo: String?,
    val recordDate: String,
    val isMainRecord: Boolean?
)
