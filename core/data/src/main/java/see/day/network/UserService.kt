package see.day.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.user.FullUserResponse

interface UserService {

    @POST("api/users/onboarding/complete")
    suspend fun postOnboardComplete(@Body requestBody: RequestBody): CommonResponse<FullUserResponse>

    @GET("api/users/me")
    suspend fun getUser(): CommonResponse<FullUserResponse>

    @HTTP(method = "DELETE", path = "api/users/withdrawal", hasBody = true)
    suspend fun deleteUser(@Body requestBody: RequestBody) : Unit
}
