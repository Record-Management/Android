package see.day.domain.usecase.calendar

import see.day.domain.repository.CalendarRepository
import see.day.model.calendar.DailyRecordDetails
import javax.inject.Inject

class GetDetailDailyRecordsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {

    suspend operator fun invoke(date: String) : Result<DailyRecordDetails>{
        return calendarRepository.getDailyDetailRecords(date)
    }
}
