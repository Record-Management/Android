package see.day.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.user.FullUserResponse

interface UserService {

    @POST("api/users/onboarding/complete")
    suspend fun postOnboardComplete(@Body requestBody: RequestBody): CommonResponse<FullUserResponse>
}
