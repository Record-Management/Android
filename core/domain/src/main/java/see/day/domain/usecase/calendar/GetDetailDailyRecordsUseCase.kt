package see.day.domain.usecase.calendar

import see.day.domain.repository.CalendarRepository
import see.day.model.calendar.DailyDetailRecords
import javax.inject.Inject

class GetDetailDailyRecordsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {

    suspend operator fun invoke(date: String) : Result<DailyDetailRecords>{
        return calendarRepository.getDailyDetailRecords(date)
    }
}
