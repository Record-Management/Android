package see.day.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.auth.RefreshTokenResponse

interface AuthService {

    @POST("api/auth/refresh")
    suspend fun refresh(@Body requestBody: RequestBody): CommonResponse<RefreshTokenResponse>
}
