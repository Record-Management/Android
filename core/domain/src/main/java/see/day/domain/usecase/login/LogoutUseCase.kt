package see.day.domain.usecase.login

import see.day.domain.repository.LoginRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(allDevices: Boolean = true) : Result<Unit>{
        return loginRepository.logout(allDevices)
    }
}
