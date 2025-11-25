package see.day.domain.usecase.login

import see.day.domain.repository.LoginRepository
import javax.inject.Inject

class GetAppFirstLaunchUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke() : Boolean {
        return loginRepository.isAppFirstLaunch()
    }
}
