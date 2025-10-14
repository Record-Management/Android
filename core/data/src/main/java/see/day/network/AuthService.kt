package see.day.network

import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.auth.LogoutRequest
import see.day.network.dto.auth.RefreshTokenRequest
import see.day.network.dto.auth.RefreshTokenResponse
import see.day.network.dto.login.LoginRequest
import see.day.network.dto.login.LoginResponse

interface AuthService {

    @POST("api/auth/social-login")
    suspend fun signIn(@Body loginRequest: LoginRequest): CommonResponse<LoginResponse>

    @POST("api/auth/refresh")
    suspend fun refresh(@Body refreshTokenRequest: RefreshTokenRequest): CommonResponse<RefreshTokenResponse>

    @POST("api/auth/logout")
    suspend fun logout(@Body logoutRequest: LogoutRequest) : CommonResponse<Unit>
}
