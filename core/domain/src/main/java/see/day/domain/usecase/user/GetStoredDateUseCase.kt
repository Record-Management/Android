package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import javax.inject.Inject

class GetStoredDateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<String?> {
        return userRepository.getStoredDate()
    }
}
