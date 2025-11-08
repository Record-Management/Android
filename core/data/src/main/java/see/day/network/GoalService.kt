package see.day.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.goal.CurrentGoalResponse

interface GoalService {

    @POST("api/goals/new")
    suspend fun postNewGoal(@Header("userId") userId: String) : CommonResponse<Unit>

    @GET("api/goals/current")
    suspend fun getCurrentGoal(@Header("userId") userId: String) : CommonResponse<CurrentGoalResponse>
}
