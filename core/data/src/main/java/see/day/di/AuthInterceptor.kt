package see.day.di

import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import see.day.datastore.DataStoreDataSource
import see.day.network.dto.CommonResponse
import see.day.network.dto.toResponseBody
import timber.log.Timber

class AuthInterceptor @Inject constructor(
    private val dataStore: DataStoreDataSource
) : Interceptor {

    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken: String = runBlocking {
            dataStore.getAccessToken().first()
        } ?: return errorResponse(request = chain.request())

        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader(AUTHORIZATION, "Bearer $accessToken")
            .build()

        try {
            val response = chain.proceed(request)

            if (response.code == HTTP_OK) {
                val bodyString = response.peekBody(Long.MAX_VALUE).string()

                val jsonObject = JSONObject(bodyString)
                val dataObject = jsonObject.optJSONObject("data")
                val newAccessToken = dataObject?.optString("accessToken", null) ?: return response
                val newRefreshToken = dataObject.optString("refreshToken", null) ?: return response

                CoroutineScope(Dispatchers.IO).launch {
                    if (accessToken != newAccessToken) {
                        dataStore.saveAccessToken(accessToken)
                        dataStore.saveRefreshToken(newRefreshToken)
                    }
                }

                val newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader(AUTHORIZATION, "Bearer $newAccessToken")
                    .build()
                return chain.proceed(newRequest)
            } else {
                response.newBuilder()
                    .code(response.code)
            }
            return response
        } catch (e: IllegalStateException) {
            Timber.e(e.toString())
            return refreshTokenExpiredResponse(request)
        }
    }

    private fun errorResponse(request: Request): Response {
        val responseBody = CommonResponse<Unit>(
            statusCode = HTTP_UNAUTHORIZED,
            code = "M01",
            message = "",
            data = null
        )

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_2)
            .code(HTTP_UNAUTHORIZED)
            .message("유효하지 않은 엑세스 토큰입니다.")
            .body(toResponseBody(responseBody))
            .build()
    }

    private fun refreshTokenExpiredResponse(request: Request): Response {
        val responseBody = CommonResponse<Unit>(
            statusCode = HTTP_UNAUTHORIZED,
            code = "M01",
            message = "",
            data = null
        )

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_2)
            .code(HTTP_UNAUTHORIZED)
            .message("유효하지 않은 리프레쉬 토큰입니다.")
            .body(toResponseBody(responseBody))
            .build()
    }
}
