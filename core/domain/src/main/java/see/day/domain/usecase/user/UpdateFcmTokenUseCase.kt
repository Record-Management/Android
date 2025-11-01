package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import javax.inject.Inject

class UpdateFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(fcmToken: String) : Result<Unit> {
        return userRepository.updateFcmToken(fcmToken)
    }
}
