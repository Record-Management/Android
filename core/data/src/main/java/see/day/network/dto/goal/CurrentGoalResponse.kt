package see.day.network.dto.goal

import kotlinx.serialization.Serializable
import see.day.model.goal.TreeStage
import see.day.model.record.RecordType
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class CurrentGoalResponse(
    val goalId: String?,
    val recordType: RecordType?,
    val goalDays: Int,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val startDate: String? = null,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val endDate: String? = null,
    val completedDays: Int,
    val achievementRate: Double,
    val treeStage: TreeStage?,
    val canCreateNew: Boolean
)
