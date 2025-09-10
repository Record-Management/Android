package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import see.day.model.user.OnboardingComplete
import javax.inject.Inject

class PostOnboardCompleteUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(onboardingComplete: OnboardingComplete) : Result<Unit> {
        return userRepository.onboardComplete(onboardingComplete)
    }
}
