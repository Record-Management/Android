package see.day.domain.repository

import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.RegisteredDailyRecord

interface DailyRecordRepository {

    suspend fun insertRecord(createDailyRecord: CreateDailyRecord) : Result<RegisteredDailyRecord>

}
