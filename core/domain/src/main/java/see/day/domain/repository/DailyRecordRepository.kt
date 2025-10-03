package see.day.domain.repository

import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.DailyRecordDetail
import see.day.model.record.daily.DailyRecordEdit

interface DailyRecordRepository {

    suspend fun insertRecord(createDailyRecord: CreateDailyRecord): Result<DailyRecordDetail>

    suspend fun editRecord(dailyRecordEdit: DailyRecordEdit) : Result<DailyRecordDetail>
}
