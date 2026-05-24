package see.day.network.dto.record

data class RecordsLimitResponse(
    val canCreateRecord: Boolean,
    val canCreateSchedule: Boolean,
)
