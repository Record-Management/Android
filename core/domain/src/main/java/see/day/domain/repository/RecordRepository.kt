package see.day.domain.repository

import see.day.model.record.daily.DailyRecordDetail

interface RecordRepository {

    suspend fun getRecord(recordId: String) : Result<DailyRecordDetail>
}
