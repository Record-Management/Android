package see.day.domain.repository

import see.day.model.calendar.DailyRecordDetails
import see.day.model.calendar.MonthlyRecord

interface CalendarRepository {
    suspend fun getMonthlyRecords(year: Int,month: Int,types: Array<String>) : Result<MonthlyRecord>
    suspend fun getDailyRecords(date: String) : Result<DailyRecordDetails>
}
