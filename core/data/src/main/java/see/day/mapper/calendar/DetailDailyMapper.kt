package see.day.mapper.calendar

import see.day.model.calendar.DailyDetailRecord
import see.day.model.calendar.DetailRecord
import see.day.model.record.RecordType
import see.day.network.dto.calendar.DailyDetailRecordResponse
import see.day.network.dto.calendar.DetailRecordResponse

fun DailyDetailRecordResponse.toModel(): DailyDetailRecord {
    return DailyDetailRecord(
        date = date,
        records = records.toModel()
    )
}

fun List<DetailRecordResponse>.toModel() : List<DetailRecord> {
    return this.map { it.toModel() }
}

fun DetailRecordResponse.toModel() : DetailRecord {
    return DetailRecord(
        id = id,
        type = RecordType.valueOf(type),
        emotion = emotion,
        content = content,
        imageUrls = imageUrls,
        recordDate = recordDate,
        recordTime = recordTime,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
