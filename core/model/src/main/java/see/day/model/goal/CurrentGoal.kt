package see.day.model.goal

import see.day.model.record.RecordType

data class CurrentGoal(
    val goalId: String?,
    val recordType: RecordType?,
    val goalDays: Int,
    val startDate: String?,
    val endDate: String?,
    val completedDays: Int,
    val achievementRate: Double,
    val treeStage: TreeStage?,
    val canCreateNew: Boolean
)
