package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import see.day.model.user.User
import see.day.model.user.UserProfileChangedInput
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userProfileChangedInput: UserProfileChangedInput): Result<User> {
        return userRepository.updateUser(userProfileChangedInput)
    }
}
