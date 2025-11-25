package see.day.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.goal.CurrentGoalResponse
import see.day.network.dto.goal.GoalReportResponse
import see.day.network.dto.goal.NewGoalRequest
import see.day.network.dto.goal.NewGoalResponse

interface GoalService {

    @POST("api/goals/new")
    suspend fun postNewGoal(@Body newGoalRequest: NewGoalRequest) : CommonResponse<NewGoalResponse>

    @GET("api/goals/current")
    suspend fun getCurrentGoal() : CommonResponse<CurrentGoalResponse?>

    @GET("api/goals/achievement/report")
    suspend fun getGoalReport() : CommonResponse<GoalReportResponse>

    // TODO 테스트용 API 추후에 삭제 예정
    @PATCH("api/goals/current/force-complete")
    suspend fun deleteCurrentGoal() : Unit
}
