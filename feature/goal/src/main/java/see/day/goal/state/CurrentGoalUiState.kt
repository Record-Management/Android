package see.day.goal.state

import see.day.model.goal.TreeStage
import see.day.model.record.RecordType

data class CurrentGoalUiState(
    val userId: String,
    val treeStage: TreeStage,
    val recordType: RecordType,
    val startDate: String,
    val endDate: String,
    val achievementRate: Int,
    val completedDays: Int,
    val dayStreak: Int,
    val isCompleted: Boolean
) {
    companion object {
        val init = CurrentGoalUiState(
            userId = "",
            treeStage = TreeStage.STAGE_1,
            recordType = RecordType.DAILY,
            startDate = "2025-11-01",
            endDate = "2025-11-10",
            achievementRate = 10,
            completedDays = 1,
            dayStreak = 1,
            isCompleted = true
        )
    }
}
