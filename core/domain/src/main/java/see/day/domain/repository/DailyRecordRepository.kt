package see.day.domain.repository

import see.day.model.record.daily.DailyRecordInput
import see.day.model.record.daily.DailyRecordDetail
import see.day.model.record.daily.DailyRecordEdit

interface DailyRecordRepository {

    suspend fun insertRecord(dailyRecordInput: DailyRecordInput): Result<DailyRecordDetail>

    suspend fun updateRecord(dailyRecordEdit: DailyRecordEdit) : Result<DailyRecordDetail>
}
