package see.day.domain.repository

import see.day.model.login.SocialLogin
import see.day.model.navigation.AppStartState

interface LoginRepository {
    suspend fun login(socialLogin: SocialLogin) : Result<AppStartState>
}
