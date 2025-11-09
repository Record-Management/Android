package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import javax.inject.Inject

class UpdateStoredDateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(date: String) : Result<Unit> {
        return userRepository.updateStoredDate(date)
    }
}
