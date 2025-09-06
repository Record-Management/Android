package see.day.repository

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import see.day.datastore.DataStoreDataSource
import see.day.domain.repository.LoginRepository
import see.day.mapper.toDto
import see.day.model.exception.NoDataException
import see.day.model.login.SocialLogin
import see.day.model.navigation.AppStartState
import see.day.model.navigation.AppStartState.HOME
import see.day.model.navigation.AppStartState.ONBOARDING
import see.day.network.LoginService
import see.day.utils.ErrorUtils.createResult

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: DataStoreDataSource,
    private val loginService: LoginService
) : LoginRepository {

    override suspend fun login(socialLogin: SocialLogin): Result<AppStartState> {
        return createResult {
            val result = loginService.signIn(socialLogin.toDto().toRequestBody()).data ?: throw NoDataException()

            withContext(Dispatchers.IO) {
                dataSource.saveAccessToken(result.accessToken)
                dataSource.saveRefreshToken(result.refreshToken)
            }

            if (result.isOnboardingComplete()) {
                withContext(Dispatchers.IO) {
                    dataSource.saveAppStartState(HOME)
                }
                HOME
            } else {
                withContext(Dispatchers.IO) {
                    dataSource.saveAppStartState(ONBOARDING)
                }
                ONBOARDING
            }
        }
    }
}
