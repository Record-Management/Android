package see.day.domain.repository

import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.DailyRecordDetail

interface DailyRecordRepository {

    suspend fun insertRecord(createDailyRecord: CreateDailyRecord) : Result<DailyRecordDetail>

}
