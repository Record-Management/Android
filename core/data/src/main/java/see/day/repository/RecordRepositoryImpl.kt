package see.day.repository

import see.day.domain.repository.RecordRepository
import see.day.mapper.record.toModel
import see.day.model.exception.NoDataException
import see.day.model.record.daily.DailyRecordDetail
import see.day.network.RecordService
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordService: RecordService
) : RecordRepository {

    override suspend fun getRecord(recordId: String): Result<DailyRecordDetail> {
        return createResult {
            recordService.getRecord(recordId).data?.toModel() ?: throw NoDataException()
        }
    }

}
