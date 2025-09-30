package see.day.network

import retrofit2.http.GET
import retrofit2.http.Path
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.daily.DailyRecordDetailResponse

interface RecordService {

    @GET("api/records/{recordId}")
    suspend fun getRecord(
        @Path("recordId") recordId: String
    ): CommonResponse<DailyRecordDetailResponse>
}
