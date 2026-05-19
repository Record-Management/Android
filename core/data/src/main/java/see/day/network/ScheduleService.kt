package see.day.network

import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.schedule.ScheduleInputRequest
import see.day.network.dto.schedule.ScheduleResponse

interface ScheduleService {

    @POST("/api/schedule-records")
    suspend fun postSchedule(
        @Body scheduleInputRequest: ScheduleInputRequest
    ): ScheduleResponse
}
