package see.day.domain.repository

import see.day.model.schedule.ScheduleDetail
import see.day.model.schedule.ScheduleInput

interface ScheduleRepository {

    suspend fun insertSchedule(scheduleInput: ScheduleInput) : Result<String>

    suspend fun getSchedule(scheduleId: String) : Result<ScheduleDetail>

    suspend fun deleteSchedule(scheduleId: String) : Result<Unit>

    suspend fun updateSchedule(scheduleId: String, scheduleInput: ScheduleInput) : Result<String>
}
