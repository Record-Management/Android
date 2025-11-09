package see.day.domain.usecase.goal

import see.day.domain.repository.GoalRepository
import see.day.model.goal.CurrentGoal
import javax.inject.Inject

class GetCurrentGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {

    suspend operator fun invoke(userId: String): Result<CurrentGoal> {
        return goalRepository.getCurrentGoal(userId)
    }
}
