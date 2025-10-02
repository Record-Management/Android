package see.day.repository

import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import see.day.datastore.DataStoreDataSource
import see.day.di.ApiModule.Auth
import see.day.domain.repository.LoginRepository
import see.day.mapper.toDto
import see.day.model.exception.NoDataException
import see.day.model.login.SocialLogin
import see.day.model.navigation.AppStartState
import see.day.model.navigation.AppStartState.HOME
import see.day.model.navigation.AppStartState.LOGIN
import see.day.model.navigation.AppStartState.ONBOARDING
import see.day.network.AuthService
import see.day.network.dto.auth.LogoutRequest
import see.day.network.dto.auth.RefreshTokenRequest
import see.day.utils.ErrorUtils.createResult

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: DataStoreDataSource,
    @Auth private val authService: AuthService
) : LoginRepository {

    override suspend fun login(socialLogin: SocialLogin): Result<AppStartState> {
        return createResult {
            val result = authService.signIn(socialLogin.toDto().toRequestBody()).data ?: throw NoDataException()

            withContext(Dispatchers.IO) {
                dataSource.saveAccessToken(result.accessToken)
                dataSource.saveRefreshToken(result.refreshToken)
            }

            if (result.isOnboardingComplete()) {
                HOME
            } else {
                ONBOARDING
            }
        }
    }

    override suspend fun logout(allDevices: Boolean): Result<Unit> {
        return createResult {
            val refreshToken = dataSource.getRefreshToken().first()
            if(refreshToken.isNullOrEmpty()) {
                dataSource.clearData()
                throw NoDataException()
            }
            val logoutRequest = LogoutRequest(refreshToken, allDevices)

            authService.logout(requestBody = logoutRequest.toRequestBody())
            dataSource.clearData()
        }.onFailure {
            dataSource.clearData()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLoginState(): Flow<AppStartState> = dataSource.hasToken().flatMapLatest { hasToken ->
        flow {
            if (!hasToken) {
                emit(LOGIN)
                return@flow
            }
            val refreshTokenValue = dataSource.getRefreshToken().first()
            if(refreshTokenValue.isNullOrEmpty()) {
                emit(LOGIN)
                return@flow
            }

            val refreshToken = RefreshTokenRequest(refreshTokenValue)

            runCatching { authService.refresh(refreshToken.toRequestBody()) }
                .onSuccess { result ->
                    val response = result.data
                    if(response == null) {
                        emit(LOGIN)
                        return@flow
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        dataSource.saveAccessToken(response.accessToken)
                    }

                    return@flow if (response.user.onboardingCompleted) {
                        emit(HOME)
                    } else {
                        emit(ONBOARDING)
                    }
                }.onFailure {
                    return@flow emit(LOGIN)
                }
        }
    }
}
