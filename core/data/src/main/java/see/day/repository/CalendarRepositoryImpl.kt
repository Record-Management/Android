package see.day.repository

import javax.inject.Inject
import see.day.domain.repository.CalendarRepository
import see.day.mapper.calendar.toModel
import see.day.model.calendar.DailyRecordDetails
import see.day.model.calendar.MonthlyRecord
import see.day.model.exception.NoDataException
import see.day.network.CalendarService
import see.day.utils.ErrorUtils.createResult

class CalendarRepositoryImpl @Inject constructor(
    private val calendarService: CalendarService
) : CalendarRepository {

    override suspend fun getMonthlyRecords(year: Int, month: Int, types: Array<String>): Result<MonthlyRecord> {
        return createResult { calendarService.getMonthlyRecords(year, month, types).data?.toModel() ?: throw NoDataException() }
    }

    override suspend fun getDailyDetailRecords(date: String): Result<DailyRecordDetails> {
        return createResult { calendarService.getDailyRecords(date).data?.toModel() ?: throw NoDataException() }
    }
}
