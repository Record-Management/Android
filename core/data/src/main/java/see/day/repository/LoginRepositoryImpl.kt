package see.day.repository

import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import see.day.datastore.DataStoreDataSource
import see.day.di.ApiModule
import see.day.domain.repository.LoginRepository
import see.day.mapper.toDto
import see.day.model.exception.NoDataException
import see.day.model.login.SocialLogin
import see.day.model.navigation.AppStartState
import see.day.model.navigation.AppStartState.HOME
import see.day.model.navigation.AppStartState.ONBOARDING
import see.day.network.AuthService
import see.day.network.LoginService
import see.day.network.dto.auth.RefreshTokenRequest
import see.day.utils.ErrorUtils.createResult

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: DataStoreDataSource,
    private val loginService: LoginService,
    @ApiModule.Auth private val authService: AuthService
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

    override fun getLoginState(): Flow<AppStartState> = flow {
        if (!dataSource.hasToken().first()) {
            AppStartState.LOGIN
        } else {
            val refreshToken = (RefreshTokenRequest(dataSource.getRefreshToken().first() ?: return@flow emit(AppStartState.LOGIN)))
            runCatching { authService.refresh(refreshToken.toRequestBody()) }
                .onSuccess {
                    val response = it.data ?: return@flow emit(AppStartState.LOGIN)
                    CoroutineScope(Dispatchers.IO).launch {
                        dataSource.saveAccessToken(response.accessToken)
                    }
                    return@flow if (response.user.onboardingCompleted) {
                        emit(HOME)
                    } else {
                        emit(ONBOARDING)
                    }
                }.onFailure {
                    return@flow emit(AppStartState.LOGIN)
                }
        }

    }
}
