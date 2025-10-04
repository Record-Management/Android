package see.day.mapper.calendar

import see.day.model.calendar.DailyDetailRecord
import see.day.model.calendar.DetailRecord
import see.day.network.dto.calendar.DailyDetailRecordResponse
import see.day.network.dto.record.RecordResponse

fun DailyDetailRecordResponse.toModel(): DailyDetailRecord {
    return DailyDetailRecord(
        date = date,
        records = records.toModel()
    )
}

fun List<RecordResponse>.toModel(): List<DetailRecord> {
    return this.map { it.toModel() }
}
