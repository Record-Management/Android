package see.day.repository

import javax.inject.Inject
import see.day.domain.repository.UserRepository
import see.day.mapper.toDto
import see.day.model.exception.NoDataException
import see.day.model.record.RecordType
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

    override suspend fun getMainRecordType(): Result<RecordType> {
        return runCatching {
            RecordType.valueOf(userService.getUser().data?.mainRecordType ?: throw NoDataException())
        }
    }
}
