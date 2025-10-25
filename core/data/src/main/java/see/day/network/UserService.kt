package see.day.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import see.day.network.dto.CommonResponse
import see.day.network.dto.auth.DeleteUserRequest
import see.day.network.dto.auth.LogoutRequest
import see.day.network.dto.user.FullUserResponse
import see.day.network.dto.user.OnboardingCompleteRequest
import see.day.network.dto.user.UserProfileChangedInputRequest

interface UserService {

    @POST("api/users/onboarding/complete")
    suspend fun postOnboardComplete(@Body onboardingCompleteRequest: OnboardingCompleteRequest): CommonResponse<FullUserResponse>

    @GET("api/users/me")
    suspend fun getUser(): CommonResponse<FullUserResponse>

    @HTTP(method = "DELETE", path = "api/users/withdrawal", hasBody = true)
    suspend fun deleteUser(@Body requestBody: DeleteUserRequest) : Unit

    @PUT("api/users/profile")
    suspend fun updateUserProfile(@Body userProfileChangedInputRequest: UserProfileChangedInputRequest) : CommonResponse<FullUserResponse>

    // accessToken이 필요하므로 UserService로 옮김
    @POST("api/auth/logout")
    suspend fun logout(@Body logoutRequest: LogoutRequest) : CommonResponse<Unit>
}
