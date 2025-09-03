package see.day.domain.repository

import record.daily.model.login.SocialLogin

interface LoginRepository {
    suspend fun login(socialLogin: SocialLogin) : Result<Boolean>
}
