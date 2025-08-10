package record.daily.domain.repository

import record.daily.model.login.SocialLogin
import record.daily.model.user.User

interface UserRepository {
    suspend fun login(socialLogin: SocialLogin): User
    suspend fun logout()
    suspend fun withDrawl(reason: String)
}
