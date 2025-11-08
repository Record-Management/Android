package see.day.mapper.goal

import see.day.model.goal.CurrentGoal
import see.day.model.goal.NewGoal
import see.day.network.dto.goal.CurrentGoalResponse
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
        goalDays = goalDays,
        startDate = startDate
    )
}
