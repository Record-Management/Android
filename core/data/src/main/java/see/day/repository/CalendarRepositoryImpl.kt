package see.day.repository

import see.day.domain.repository.CalendarRepository
import see.day.mapper.calendar.toModel
import see.day.model.calendar.DailyDetailRecord
import see.day.model.calendar.MonthlyRecord
import see.day.model.exception.NoDataException
import see.day.network.CalendarService
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject


class CalendarRepositoryImpl @Inject constructor(
    private val calendarService: CalendarService
) : CalendarRepository {

    override suspend fun getMonthlyRecords(year: Int, month: Int, types: Array<String>): Result<MonthlyRecord> {
        return createResult { calendarService.getMonthlyRecords(year, month, types).data?.toModel() ?: throw NoDataException() }
    }

    override suspend fun getDailyDetailRecords(date: String): Result<DailyDetailRecord> {
        return createResult { calendarService.getDailyRecordData(date).data?.toModel() ?: throw NoDataException() }
    }

}
