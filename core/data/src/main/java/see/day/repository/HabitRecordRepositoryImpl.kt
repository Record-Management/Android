package see.day.repository

import see.day.domain.repository.HabitRecordRepository
import see.day.mapper.record.toDto
import see.day.model.calendar.HabitRecordDetail
import see.day.model.exception.NoDataException
import see.day.model.record.habit.HabitRecordInput
import see.day.network.HabitRecordService
import see.day.network.dto.record.habit.HabitRecordCompleteRequest
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject

class HabitRecordRepositoryImpl @Inject constructor(
    private val habitRecordService: HabitRecordService
) : HabitRecordRepository {

    override suspend fun insertHabitRecord(habitRecordInput: HabitRecordInput): Result<HabitRecordDetail> {
        return createResult {
            habitRecordService.postHabitRecord(habitRecordInput.toDto()).data?.toHabitRecord() ?: throw NoDataException()
        }
    }

    override suspend fun deleteHabitRecord(recordId: String): Result<Unit> {
        return createResult {
            habitRecordService.deleteHabitRecord(recordId)
        }
    }

    override suspend fun updateHabitRecordIsCompleted(recordId: String, isCompleted: Boolean): Result<Boolean> {
        return createResult {
            habitRecordService.updateHabitRecordComplete(recordId, HabitRecordCompleteRequest(isCompleted)).data?.isCompleted ?: throw NoDataException()
        }
    }
}
