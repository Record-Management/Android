package see.day.domain.usecase.goal

import see.day.domain.repository.GoalRepository
import javax.inject.Inject

class DeleteCurrentGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
){

    suspend operator fun invoke() : Result<Unit> {
        return goalRepository.deleteCurrentGoal()
    }
}
