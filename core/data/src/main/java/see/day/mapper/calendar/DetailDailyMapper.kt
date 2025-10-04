package see.day.mapper.calendar

import see.day.model.calendar.DailyDetailRecords
import see.day.model.calendar.RecordDetail
import see.day.network.dto.calendar.DailyDetailRecordResponse
import see.day.network.dto.record.RecordResponse

fun DailyDetailRecordResponse.toModel(): DailyDetailRecords {
    return DailyDetailRecords(
        date = date,
        records = records.toModel()
    )
}

fun List<RecordResponse>.toModel(): List<RecordDetail> {
    return this.map { it.toModel() }
}
