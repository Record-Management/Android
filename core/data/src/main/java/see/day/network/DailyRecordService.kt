package see.day.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.daily.DailyRecordDetailResponse

interface DailyRecordService {

    @POST("api/daily-records")
    suspend fun postDailyRecord(@Body requestBody: RequestBody): CommonResponse<DailyRecordDetailResponse>

    @PUT("api/daily-records/{recordId}")
    suspend fun putDailyRecord(
        @Path("recordId") recordId: String,
        @Body requestBody: RequestBody
    ) : CommonResponse<DailyRecordDetailResponse>
}
