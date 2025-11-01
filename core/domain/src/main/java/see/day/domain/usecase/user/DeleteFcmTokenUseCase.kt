package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import javax.inject.Inject

class DeleteFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return userRepository.deleteFcmToken()
    }
}
