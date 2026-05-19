package see.day.repository

import see.day.domain.repository.ScheduleRepository
import see.day.mapper.schedule.toDto
import see.day.model.schedule.ScheduleInput
import see.day.network.ScheduleService
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleService: ScheduleService
) : ScheduleRepository {

    override suspend fun insertSchedule(scheduleInput: ScheduleInput) : Result<String> {
        return createResult {
            scheduleService.postSchedule(scheduleInput.toDto()).scheduleRecordId
        }
    }
}
