package see.day.mapper.record

import see.day.model.record.daily.DailyRecordInput
import see.day.model.record.daily.DailyEmotion
import see.day.model.record.daily.DailyRecordDetail
import see.day.model.record.daily.DailyRecordEdit
import see.day.network.dto.record.daily.DailyRecordInputRequest
import see.day.network.dto.record.daily.DailyRecordDetailResponse
import see.day.network.dto.record.daily.DailyRecordEditRequest

fun DailyRecordInput.toDto(): DailyRecordInputRequest {
    return DailyRecordInputRequest(
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

fun DailyRecordEdit.toDto() : DailyRecordEditRequest {
    return DailyRecordEditRequest(
        content = content,
        emotion = emotion.name,
        imageUrls = imageUrls
    )
}
