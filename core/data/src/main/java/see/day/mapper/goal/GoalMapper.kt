package see.day.mapper.goal

import see.day.model.goal.CurrentGoal
import see.day.model.goal.RecentGoalReport
import see.day.model.goal.NewGoal
import see.day.network.dto.goal.CurrentGoalResponse
import see.day.network.dto.goal.GoalReportResponse
import see.day.network.dto.goal.NewGoalRequest

internal fun CurrentGoalResponse.toModel() : CurrentGoal {
    return CurrentGoal(
        goalId = goalId,
        recordType = recordType,
        goalDays = goalDays,
        startDate = startDate,
        endDate = endDate,
        completedDays = completedDays,
        achievementRate = achievementRate,
        treeStage = treeStage,
        canCreateNew = canCreateNew
    )
}

internal fun NewGoal.toDto() : NewGoalRequest {
    return NewGoalRequest(
        recordType = recordType.name,
        goalDays = goalDays
    )
}

internal fun GoalReportResponse.toCurrentGoalReport(): RecentGoalReport {
    return if(this.currentPeriod == null) {
        val currentGoalReportResponse = this.recentHistory[0]
        RecentGoalReport(
            goalId = currentGoalReportResponse.goalId,
            recordType = currentGoalReportResponse.recordType,
            goalDays = currentGoalReportResponse.goalDays,
            startDate = currentGoalReportResponse.startDate,
            endDate = currentGoalReportResponse.endDate,
            completedDays = currentGoalReportResponse.completedDays,
            achievementRate = currentGoalReportResponse.achievementRate,
            cumulativeAchievementCount = this.cumulativeAchievementCount,
            treeStage = currentGoalReportResponse.finalTreeStage,
            isGoalCompleted = currentGoalReportResponse.status == "완료"
        )
    } else {
        RecentGoalReport(
            goalId = currentPeriod.goalId,
            recordType = currentPeriod.recordType,
            goalDays = currentPeriod.goalDays,
            startDate = currentPeriod.startDate,
            endDate = currentPeriod.endDate,
            completedDays = currentPeriod.completedDays,
            achievementRate = currentPeriod.achievementRate,
            cumulativeAchievementCount = cumulativeAchievementCount,
            treeStage = currentPeriod.treeStage,
            isGoalCompleted = !currentPeriod.isInProgress
        )
    }
}
