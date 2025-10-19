package see.day.network

import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.HabitRecordResponse
import see.day.network.dto.record.habit.HabitRecordInputRequest

interface HabitRecordService {

    @POST("api/habit-records")
    suspend fun postHabitRecord(@Body habitRecordInput: HabitRecordInputRequest) : CommonResponse<HabitRecordResponse>
}
