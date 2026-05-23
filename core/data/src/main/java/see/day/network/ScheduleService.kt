package see.day.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import see.day.network.dto.schedule.ScheduleInputRequest
import see.day.network.dto.schedule.ScheduleResponse

interface ScheduleService {

    @POST("/api/schedule-records")
    suspend fun postSchedule(
        @Body scheduleInputRequest: ScheduleInputRequest
    ): ScheduleResponse

    @GET("/api/schedule-records/{scheduleRecordId}")
    suspend fun getSchedule(
        @Path("scheduleRecordId") scheduleRecordId: String
    ): ScheduleResponse

    @DELETE("/api/schedule-records/{scheduleRecordId}")
    suspend fun deleteSchedule(
        @Path("scheduleRecordId") scheduleRecordId: String
    )
}
