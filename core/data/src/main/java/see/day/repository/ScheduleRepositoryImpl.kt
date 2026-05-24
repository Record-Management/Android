package see.day.repository

import see.day.domain.repository.ScheduleRepository
import see.day.mapper.schedule.toDto
import see.day.mapper.schedule.toModel
import see.day.model.schedule.ScheduleDetail
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

    override suspend fun getSchedule(scheduleId: String): Result<ScheduleDetail> {
        return createResult {
            scheduleService.getSchedule(scheduleId).toModel()
        }
    }

    override suspend fun deleteSchedule(scheduleId: String): Result<Unit> {
        return createResult {
            scheduleService.deleteSchedule(scheduleId)
        }
    }

    override suspend fun updateSchedule(scheduleId: String, scheduleInput: ScheduleInput): Result<String> {
        return createResult {
            scheduleService.updateSchedule(scheduleId, scheduleInput.toDto()).scheduleRecordId
        }
    }
}
