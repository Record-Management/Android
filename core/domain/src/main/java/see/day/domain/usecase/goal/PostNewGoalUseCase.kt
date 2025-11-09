package see.day.domain.usecase.goal

import see.day.domain.repository.GoalRepository
import see.day.model.goal.NewGoal
import javax.inject.Inject

class PostNewGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {

    suspend operator fun invoke(userId: String, newGoal: NewGoal): Result<Unit> {
        return goalRepository.postNewGoal(userId, newGoal)
    }
}
