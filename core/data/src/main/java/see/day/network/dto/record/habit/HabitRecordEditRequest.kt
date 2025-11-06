package see.day.network.dto.record.habit

import kotlinx.serialization.Serializable

@Serializable
data class HabitRecordEditRequest(
    val habitType: String,
    val notificationEnabled: Boolean,
    val notificationTime: String?,
    val memo: String?,
    val isMainRecord: Boolean?
)
