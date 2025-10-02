package see.day.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.auth.RefreshTokenResponse
import see.day.network.dto.login.LoginResponse

interface AuthService {

    @POST("api/auth/social-login")
    suspend fun signIn(@Body requestBody: RequestBody): CommonResponse<LoginResponse>

    @POST("api/auth/refresh")
    suspend fun refresh(@Body requestBody: RequestBody): CommonResponse<RefreshTokenResponse>

    @POST("api/auth/logout")
    suspend fun logout(@Body requestBody: RequestBody) : CommonResponse<Unit>
}
