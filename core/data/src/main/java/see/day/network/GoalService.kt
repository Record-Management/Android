package see.day.network

import retrofit2.http.GET
import retrofit2.http.Header
import see.day.network.dto.CommonResponse
import see.day.network.dto.goal.CurrentGoalResponse

interface GoalService {

    @GET("api/goals/current")
    suspend fun getCurrentGoal(@Header("userId") userId: String) : CommonResponse<CurrentGoalResponse>
}
