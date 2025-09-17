package see.day.mapper.calendar

import see.day.model.calendar.DailyRecord
import see.day.model.calendar.DailyRecords
import see.day.model.calendar.MonthlyRecord
import see.day.model.record.RecordType
import see.day.network.dto.calendar.DailyRecordResponse
import see.day.network.dto.calendar.DailyRecordsResponse
import see.day.network.dto.calendar.MonthlyRecordResponse

fun MonthlyRecordResponse.toModel(): MonthlyRecord {
    return MonthlyRecord(
        year = year,
        month = month,
        dailyRecords = dailyRecords.toDailyRecordsList()
    )
}

fun List<DailyRecordsResponse>.toDailyRecordsList() : List<DailyRecords> {
    return this.map { it.toModel() }
}

fun DailyRecordsResponse.toModel() : DailyRecords {
    return DailyRecords(
        date = date,
        records = records.toDailyRecordList()
    )
}

fun List<DailyRecordResponse>.toDailyRecordList() : List<DailyRecord> {
    return this.map { it.toModel() }
}

fun DailyRecordResponse.toModel() : DailyRecord {
    return DailyRecord(
        id = id,
        type = RecordType.valueOf(type),
        emotion = emotion
    )
}
