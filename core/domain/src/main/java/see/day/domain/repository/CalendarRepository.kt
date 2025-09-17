package see.day.domain.repository

import see.day.model.calendar.MonthlyRecord

interface CalendarRepository {
    suspend fun getMonthlyRecords(year: Int,month: Int,types: Array<String>) : Result<MonthlyRecord>
}
