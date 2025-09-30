package see.day.mapper.record

import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.DailyEmotion
import see.day.model.record.daily.DailyRecordDetail
import see.day.model.record.daily.ModifyDailyRecord
import see.day.network.dto.record.daily.DailyRecordDetailRequest
import see.day.network.dto.record.daily.DailyRecordDetailResponse
import see.day.network.dto.record.daily.ModifyDailyRecordRequest

fun CreateDailyRecord.toDto(): DailyRecordDetailRequest {
    return DailyRecordDetailRequest(
        content = content,
        emotion = emotion.name,
        recordDate = recordDate.formatDate(),
        recordTime = recordDate.formatTime(),
        imageUrls = imageUrls
    )
}

fun DailyRecordDetailResponse.toModel(): DailyRecordDetail {
    return DailyRecordDetail(
        id = id,
        type = type,
        emotion = DailyEmotion.valueOf(emotion),
        content = content,
        imageUrls = imageUrls,
        recordDate = recordDate,
        recordTime = recordTime,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun ModifyDailyRecord.toDto() : ModifyDailyRecordRequest {
    return ModifyDailyRecordRequest(
        content = content,
        emotion = emotion.name,
        imageUrls = imageUrls
    )
}
