package see.day.network.dto.notification

import kotlinx.serialization.Serializable
import see.day.model.record.RecordType
import see.day.network.dto.PageInfoResponse
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class NotificationHistoryResponse(
    val notifications: NotificationHistoryDataResponse,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recentCheckedAt: String
)

@Serializable
data class NotificationHistoryDataResponse(
    val items: List<NotificationHistoryItemResponse>,
    val pageInfo: PageInfoResponse
)

@Serializable
data class NotificationHistoryItemResponse(
    val mainRecordType: RecordType,
    val description: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val sentAt: String
)
