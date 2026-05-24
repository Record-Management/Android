package see.day.network.dto.record

import kotlinx.serialization.Serializable

@Serializable
data class RecordsLimitResponse(
    val canCreateRecord: Boolean,
    val canCreateSchedule: Boolean,
)
