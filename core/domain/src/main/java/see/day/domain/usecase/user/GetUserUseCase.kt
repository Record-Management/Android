package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import see.day.model.user.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<User> {
        return userRepository.getUser()
    }
}
