package see.day.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import see.day.network.dto.CommonResponse
import see.day.network.dto.calendar.DailyDetailRecordResponse
import see.day.network.dto.calendar.MonthlyRecordResponse

interface CalendarService {

    @GET("/api/calendar/{year}/{month}")
    suspend fun getMonthlyRecords(@Path("year") year: Int, @Path("month") month: Int, @Query("types") types: Array<String>): CommonResponse<MonthlyRecordResponse>

    @GET("api/records/date/{date}")
    suspend fun getDailyRecordData(@Path("date") date: String): CommonResponse<DailyDetailRecordResponse>
}
