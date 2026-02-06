package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import javax.inject.Inject

class GetIsShownTutorialUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<Boolean> {
        return userRepository.getIsShownTutorial()
    }
}
