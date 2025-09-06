package see.day.domain.repository

import see.day.model.login.SocialLogin

interface LoginRepository {
    suspend fun login(socialLogin: SocialLogin) : Result<Boolean>
}
