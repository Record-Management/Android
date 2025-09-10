package see.day.domain.usecase.login

import kotlinx.coroutines.flow.Flow
import see.day.domain.repository.LoginRepository
import see.day.model.navigation.AppStartState
import javax.inject.Inject

class GetLoginStateUseCase @Inject constructor(
    private val loginRepository: LoginRepository
){
    operator fun invoke() : Flow<AppStartState> {
        return loginRepository.getLoginState()
    }
}
