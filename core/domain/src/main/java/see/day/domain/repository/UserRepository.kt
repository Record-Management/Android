package see.day.domain.repository

import see.day.model.user.OnboardingComplete

interface UserRepository {
    suspend fun onboardComplete(onboardingComplete: OnboardingComplete) : Result<Unit>
}
