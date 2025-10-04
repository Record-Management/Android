package see.day.mapper.record

import see.day.model.record.daily.DailyRecordInput
import see.day.model.record.daily.DailyRecordEdit
import see.day.network.dto.record.daily.DailyRecordInputRequest
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

fun DailyRecordEdit.toDto() : DailyRecordEditRequest {
    return DailyRecordEditRequest(
        content = content,
        emotion = emotion.name,
        imageUrls = imageUrls
    )
}
