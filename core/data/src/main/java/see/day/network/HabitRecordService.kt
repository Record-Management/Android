package see.day.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.HabitRecordResponse
import see.day.network.dto.record.habit.HabitRecordInputRequest

interface HabitRecordService {

    @POST("api/habit-records")
    suspend fun postHabitRecord(@Body habitRecordInput: HabitRecordInputRequest): CommonResponse<HabitRecordResponse>

    @DELETE("api/habit-records/{habitRecordId}")
    suspend fun deleteHabitRecord(@Path("habitRecordId") recordId: String): CommonResponse<Unit>
}
