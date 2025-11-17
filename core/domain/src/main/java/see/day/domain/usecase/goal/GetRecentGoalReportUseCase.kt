package see.day.domain.usecase.goal

import see.day.domain.repository.GoalRepository
import see.day.model.goal.RecentGoalReport
import javax.inject.Inject

class GetRecentGoalReportUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {

    suspend operator fun invoke() : Result<RecentGoalReport> {
        return goalRepository.getRecentGoalReport()
    }
}
