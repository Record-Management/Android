package see.day.mapper.record

import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.RegisteredDailyRecord
import see.day.network.dto.record.daily.DailyRecordDetailRequest
import see.day.network.dto.record.daily.DailyRecordDetailResponse

fun CreateDailyRecord.toDto(): DailyRecordDetailRequest {
    return DailyRecordDetailRequest(
        content = content,
        emotion = emotion,
        recordDate = recordDate.formatDate(),
        recordTime = recordDate.formatTime(),
        imageUrls = imageUrls
    )
}

fun DailyRecordDetailResponse.toModel(): RegisteredDailyRecord {
    return RegisteredDailyRecord(
        id = id,
        type = type,
        emotion = emotion,
        content = content,
        imageUrls = imageUrls,
        recordDate = recordDate,
        recordTime = recordTime,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
