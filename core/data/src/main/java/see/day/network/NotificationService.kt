package see.day.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import see.day.network.dto.CommonResponse
import see.day.network.dto.notification.NotificationHistoryResponse
import see.day.network.dto.notification.NotificationSettingResponse
import see.day.network.dto.notification.NotificationSettingsEditRequest
import see.day.network.dto.notification.UpdatedCountResponse

interface NotificationService {

    @GET("api/notifications/history")
    suspend fun getNotificationHistory(): CommonResponse<NotificationHistoryResponse>

    @PUT("api/notifications/mark-all-read")
    suspend fun updateNotificationHistoryAllRead(): CommonResponse<UpdatedCountResponse>

    @GET("api/notifications/settings")
    suspend fun getNotificationSetting(): CommonResponse<NotificationSettingResponse>

    @PUT("api/notifications/settings")
    suspend fun updateNotificationSetting(@Body notificationSettingsEditRequest: NotificationSettingsEditRequest) : CommonResponse<NotificationSettingResponse>
}
