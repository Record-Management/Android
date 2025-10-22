package see.day.repository

import see.day.datastore.DataStoreDataSource
import javax.inject.Inject
import see.day.domain.repository.UserRepository
import see.day.mapper.toDto
import see.day.mapper.toModel
import see.day.model.exception.NoDataException
import see.day.model.record.RecordType
import see.day.model.user.OnboardingComplete
import see.day.model.user.User
import see.day.model.user.UserProfileChangedInput
import see.day.network.UserService
import see.day.network.dto.auth.DeleteUserRequest
import see.day.utils.ErrorUtils.createResult

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val dataSource: DataStoreDataSource
) : UserRepository {

    override suspend fun onboardComplete(onboardingComplete: OnboardingComplete): Result<Unit> {
        return createResult {
            userService.postOnboardComplete(onboardingComplete.toDto())
        }
    }

    override suspend fun getMainRecordType(): Result<RecordType> {
        return createResult {
            RecordType.valueOf(userService.getUser().data?.mainRecordType ?: throw NoDataException())
        }
    }

    override suspend fun getUser(): Result<User> {
        return createResult {
            userService.getUser().data?.toModel() ?: throw NoDataException()
        }
    }

    override suspend fun deleteUser(): Result<Unit> {
        return createResult {
            userService.deleteUser(DeleteUserRequest("테스트용도"))
            dataSource.clearData()
        }.onFailure {
            dataSource.clearData()
        }
    }

    override suspend fun updateUser(updateUserProfileChangedInput: UserProfileChangedInput): Result<User> {
        return createResult {
            userService.updateUserProfile(updateUserProfileChangedInput.toDto()).data?.toModel() ?: throw NoDataException()
        }
    }
}
