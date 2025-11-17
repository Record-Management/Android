package see.day.model.goal

import see.day.model.record.RecordType

data class RecentGoalReport(
    val goalId: String,
    val recordType: RecordType,
    val goalDays: Int,
    val startDate: String,
    val endDate: String,
    val completedDays: Int,
    val achievementRate: Double,
    val cumulativeAchievementCount: Int,
    val treeStage: TreeStage,
    val isGoalCompleted: Boolean
)
