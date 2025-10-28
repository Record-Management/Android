package see.day.network.dto.notification

import kotlinx.serialization.Serializable
import see.day.network.decoder.FlexibleDateTimeArraySerializer
import see.day.network.dto.PageInfoResponse

@Serializable
data class NotificationHistoryResponse(
    val notifications: NotificationHistoryDataResponse,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recentCheckedAt: String? = null
)

@Serializable
data class NotificationHistoryDataResponse(
    val items: List<NotificationHistoryItemResponse>,
    val pageInfo: PageInfoResponse
)

@Serializable
data class NotificationHistoryItemResponse(
    val mainRecordType: String,
    val description: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val sentAt: String
)
