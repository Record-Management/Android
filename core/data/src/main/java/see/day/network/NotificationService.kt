package see.day.network

import retrofit2.http.GET
import retrofit2.http.PUT
import see.day.network.dto.CommonResponse
import see.day.network.dto.notification.NotificationHistoryResponse

interface NotificationService {

    @GET("api/notifications/history")
    suspend fun getNotificationHistory(): CommonResponse<NotificationHistoryResponse>

    @PUT("api/notifications/mark-all-read")
    suspend fun updateNotificationHistoryAllRead() : CommonResponse<Int>
}
