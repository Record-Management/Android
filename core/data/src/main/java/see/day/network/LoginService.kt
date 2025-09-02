package see.day.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.login.LoginResponse

interface LoginService {

    @POST("api/auth/social-login")
    suspend fun signIn(@Body requestBody: RequestBody): CommonResponse<LoginResponse>
}
