package see.day.mapper.goal

import see.day.model.goal.CurrentGoal
import see.day.network.dto.goal.CurrentGoalResponse

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
