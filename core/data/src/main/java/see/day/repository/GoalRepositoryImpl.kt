package see.day.repository

import see.day.domain.repository.GoalRepository
import see.day.mapper.goal.toCurrentGoalReport
import see.day.mapper.goal.toDto
import see.day.mapper.goal.toModel
import see.day.model.exception.NoDataException
import see.day.model.goal.CurrentGoal
import see.day.model.goal.NewGoal
import see.day.model.goal.RecentGoalReport
import see.day.network.GoalService
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalService: GoalService
) : GoalRepository{

    override suspend fun postNewGoal(newGoal: NewGoal): Result<Unit> {
        return createResult {
            goalService.postNewGoal(newGoal.toDto())
        }
    }

    override suspend fun getCurrentGoal(): Result<CurrentGoal?> {
        return createResult {
            goalService.getCurrentGoal().data?.toModel()
        }
    }

    override suspend fun getRecentGoalReport(): Result<RecentGoalReport> {
        return createResult {
            goalService.getGoalReport().data?.toCurrentGoalReport() ?: throw NoDataException()
        }
    }
}
