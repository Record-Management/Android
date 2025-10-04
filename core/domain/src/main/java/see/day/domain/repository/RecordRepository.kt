package see.day.domain.repository

import see.day.model.calendar.RecordDetail

interface RecordRepository {

    suspend fun getRecord(recordId: String) : Result<RecordDetail>
}
