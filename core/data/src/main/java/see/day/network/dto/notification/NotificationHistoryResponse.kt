package see.day.network.dto.notification

import kotlinx.serialization.Serializable
import see.day.model.record.RecordType
import see.day.network.PageInfoResponse
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class NotificationHistoryResponse(
    val notifications: List<NotificationHistoryDataResponse>,
    val pageInfo: PageInfoResponse,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recentCheckedAt: String
)

@Serializable
data class NotificationHistoryDataResponse(
    val mainRecordType: RecordType,
    val description: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val sentAt: String
)
