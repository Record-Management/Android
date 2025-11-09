package see.day.domain.repository

import see.day.model.goal.CurrentGoal
import see.day.model.goal.NewGoal

interface GoalRepository {

    suspend fun postNewGoal(userId: String, newGoal: NewGoal): Result<Unit>

    suspend fun getCurrentGoal(userId: String): Result<CurrentGoal>
}
