package see.day.repository

import javax.inject.Inject
import see.day.domain.repository.DailyRecordRepository
import see.day.mapper.record.toDto
import see.day.mapper.record.toModel
import see.day.model.exception.NoDataException
import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.DailyRecordDetail
import see.day.model.record.daily.DailyRecordEdit
import see.day.network.DailyRecordService
import see.day.utils.ErrorUtils.createResult

class DailyRecordRepositoryImpl @Inject constructor(
    private val dailyRecordService: DailyRecordService
) : DailyRecordRepository {

    override suspend fun insertRecord(createDailyRecord: CreateDailyRecord): Result<DailyRecordDetail> {
        return createResult {
            dailyRecordService.postDailyRecord(createDailyRecord.toDto().toRequestBody()).data?.toModel() ?: throw NoDataException()
        }
    }

    override suspend fun editRecord(dailyRecordEdit: DailyRecordEdit) : Result<DailyRecordDetail> {
        return createResult {
            dailyRecordService.putDailyRecord(dailyRecordEdit.recordId, dailyRecordEdit.toDto().toRequestBody()).data?.toModel() ?: throw NoDataException()
        }
    }
}
