package see.day.domain.repository

import see.day.model.schedule.ScheduleInput

interface ScheduleRepository {

    suspend fun insertSchedule(scheduleInput: ScheduleInput) : Result<String>
}
