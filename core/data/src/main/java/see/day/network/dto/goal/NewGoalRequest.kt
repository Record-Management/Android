package see.day.network.dto.goal

import kotlinx.serialization.Serializable

@Serializable
data class NewGoalRequest(
    val recordType: String,
    val goalDays: Int,
    val startDate: String
)
