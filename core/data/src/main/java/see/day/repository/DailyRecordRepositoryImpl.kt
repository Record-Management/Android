package see.day.repository

import see.day.domain.repository.DailyRecordRepository
import see.day.mapper.record.toDto
import see.day.mapper.record.toModel
import see.day.model.exception.NoDataException
import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.RegisteredDailyRecord
import see.day.network.DailyRecordService
import javax.inject.Inject

class DailyRecordRepositoryImpl @Inject constructor(
    private val dailyRecordService: DailyRecordService
) : DailyRecordRepository {

    override suspend fun insertRecord(createDailyRecord: CreateDailyRecord): Result<RegisteredDailyRecord> {
        return runCatching {
            dailyRecordService.postDailyRecord(createDailyRecord.toDto().toRequestBody()).data?.toModel() ?: throw NoDataException()
        }
    }
}
