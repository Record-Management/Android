package see.day.domain.repository

import see.day.model.goal.CurrentGoal
import see.day.model.goal.NewGoal

interface GoalRepository {

    suspend fun postNewGoal(newGoal: NewGoal): Result<Unit>

    suspend fun getCurrentGoal(): Result<CurrentGoal?>
}
