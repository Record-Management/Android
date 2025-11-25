package see.day.domain.repository

import see.day.model.goal.CurrentGoal
import see.day.model.goal.NewGoal
import see.day.model.goal.RecentGoalReport

interface GoalRepository {

    suspend fun postNewGoal(newGoal: NewGoal): Result<Unit>

    suspend fun getCurrentGoal(): Result<CurrentGoal?>

    suspend fun getRecentGoalReport() : Result<RecentGoalReport>

    // TODO 테스트용 API 추후 삭제 예정
    suspend fun deleteCurrentGoal() : Result<Unit>
}
