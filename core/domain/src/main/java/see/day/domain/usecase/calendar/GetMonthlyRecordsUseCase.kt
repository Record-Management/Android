package see.day.domain.usecase.calendar

import see.day.domain.repository.CalendarRepository
import see.day.model.calendar.MonthlyRecord
import javax.inject.Inject

class GetMonthlyRecordsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {

    suspend operator fun invoke(year: Int,month: Int, types: Array<String>) : Result<MonthlyRecord> {
        return calendarRepository.getMonthlyRecords(year, month, types)
    }
}
