package see.day.mapper.calendar

import see.day.mapper.schedule.toModel
import see.day.model.calendar.DailyRecordDetails
import see.day.model.calendar.HabitRecordDetail
import see.day.model.calendar.RecordDetail
import see.day.model.schedule.DailySchedule
import see.day.network.dto.calendar.DailyDetailRecordResponse
import see.day.network.dto.record.RecordResponse
import see.day.network.dto.schedule.DailyScheduleResponse

fun DailyDetailRecordResponse.toModel(): DailyRecordDetails {
    return DailyRecordDetails(
        date = date,
        records = records.toModel(),
        schedules = schedules.toScheduleModel(),
    )
}

fun List<RecordResponse>.toModel(): List<RecordDetail> {
    return this.map { it.toModel() }
        .sortedByDescending { record ->
            if (record is HabitRecordDetail) {
                record.isMainRecord
            } else {
                false
            }
        }
}

fun List<DailyScheduleResponse>.toScheduleModel(): List<DailySchedule> {
    return this.map { it.toModel() }
}
