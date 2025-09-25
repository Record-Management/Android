package see.day.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.dailyRecord.DailyRecordDetailResponse

interface DailyRecordService {

    @POST("api/daily-records")
    suspend fun postDailyRecord(@Body requestBody: RequestBody) : CommonResponse<DailyRecordDetailResponse>
}
