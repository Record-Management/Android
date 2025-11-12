package see.day.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.goal.CurrentGoalResponse
import see.day.network.dto.goal.NewGoalRequest

interface GoalService {

    @POST("api/goals/new")
    suspend fun postNewGoal(@Body newGoalRequest: NewGoalRequest) : Unit

    @GET("api/goals/current")
    suspend fun getCurrentGoal() : CommonResponse<CurrentGoalResponse?>
}
