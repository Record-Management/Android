package see.day.repository

import see.day.domain.repository.RecordRepository
import see.day.model.calendar.RecordDetail
import see.day.model.exception.NoDataException
import see.day.network.RecordService
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordService: RecordService
) : RecordRepository {

    override suspend fun getRecord(recordId: String): Result<RecordDetail> {
        return createResult {
            recordService.getRecord(recordId).data?.toModel() ?: throw NoDataException()
        }
    }

}
