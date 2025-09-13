package see.day.repository

import javax.inject.Inject
import see.day.datastore.DataStoreDataSource
import see.day.domain.repository.UserRepository
import see.day.mapper.toDto
import see.day.model.navigation.AppStartState
import see.day.model.user.OnboardingComplete
import see.day.network.UserService

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun onboardComplete(onboardingComplete: OnboardingComplete): Result<Unit> {
        return runCatching {
            userService.postOnboardComplete(onboardingComplete.toDto().toRequestBody())
        }
    }
}
