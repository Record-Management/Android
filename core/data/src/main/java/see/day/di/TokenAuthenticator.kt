package see.day.di

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import see.day.datastore.DataStoreDataSource
import see.day.di.ApiModule.Auth
import see.day.network.AuthService
import see.day.network.dto.auth.RefreshTokenRequest

class TokenAuthenticator @Inject constructor(
    private val dataStore: DataStoreDataSource,
    @Auth private val authService: AuthService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null
        return runBlocking {
            runCatching {
                val refreshToken = dataStore.getRefreshToken().first() ?: throw IllegalStateException("Refresh token is null")

                val tokenResponse = authService.refresh(RefreshTokenRequest(refreshToken))
                if (tokenResponse.statusCode != 200 && tokenResponse.data == null) {
                    throw IllegalStateException("refreshToken 발급 실패")
                }
                val tokenData = tokenResponse.data?.accessToken ?: throw IllegalStateException("token is null")

                withContext(Dispatchers.IO) {
                    dataStore.saveAccessToken(tokenData)
                }

                response.request.newBuilder()
                    .header("Authorization", "Bearer $tokenData")
                    .build()
            }.getOrElse {
                dataStore.clearData()
                null
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var r = response.priorResponse
        while (r != null) {
            count++
            r = r.priorResponse
        }
        return count
    }
}
