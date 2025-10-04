package see.day.mapper.calendar

import see.day.model.calendar.DailyRecordDetails
import see.day.model.calendar.RecordDetail
import see.day.network.dto.calendar.DailyDetailRecordResponse
import see.day.network.dto.record.RecordResponse

fun DailyDetailRecordResponse.toModel(): DailyRecordDetails {
    return DailyRecordDetails(
        date = date,
        records = records.toModel()
    )
}

fun List<RecordResponse>.toModel(): List<RecordDetail> {
    return this.map { it.toModel() }
}
