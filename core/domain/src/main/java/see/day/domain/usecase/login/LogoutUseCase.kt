package see.day.domain.usecase.login

import see.day.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(allDevices: Boolean = true) : Result<Unit>{
        return userRepository.logout(allDevices)
    }
}
