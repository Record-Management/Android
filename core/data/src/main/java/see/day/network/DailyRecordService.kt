package see.day.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.DailyRecordResponse
import see.day.network.dto.record.daily.DailyRecordInputRequest

interface DailyRecordService {

    @POST("api/daily-records")
    suspend fun postDailyRecord(@Body dailyRecordInputRequest: DailyRecordInputRequest): CommonResponse<DailyRecordResponse>

    @PUT("api/daily-records/{recordId}")
    suspend fun updateDailyRecord(
        @Path("recordId") recordId: String,
        @Body requestBody: RequestBody
    ) : CommonResponse<DailyRecordResponse>

    @DELETE("api/daily-records/{recordId}")
    suspend fun deleteDailyRecord(
        @Path("recordId") recordId: String
    ) : CommonResponse<Unit>
}
