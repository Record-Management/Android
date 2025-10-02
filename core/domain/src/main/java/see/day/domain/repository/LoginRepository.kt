package see.day.domain.repository

import kotlinx.coroutines.flow.Flow
import see.day.model.login.SocialLogin
import see.day.model.navigation.AppStartState

interface LoginRepository {
    suspend fun login(socialLogin: SocialLogin) : Result<AppStartState>
    suspend fun logout(allDevices: Boolean) : Result<Unit>
    fun getLoginState() : Flow<AppStartState>
}
