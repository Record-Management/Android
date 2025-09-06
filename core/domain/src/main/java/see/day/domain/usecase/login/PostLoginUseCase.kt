package see.day.domain.usecase.login

import see.day.domain.repository.LoginRepository
import see.day.model.login.SocialLogin
import see.day.model.navigation.AppStartState
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(socialLogin: SocialLogin): Result<AppStartState> {
        return loginRepository.login(socialLogin)
    }
}
