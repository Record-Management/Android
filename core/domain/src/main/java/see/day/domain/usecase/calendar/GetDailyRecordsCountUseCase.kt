package see.day.domain.usecase.calendar

import see.day.domain.repository.CalendarRepository
import javax.inject.Inject

class GetDailyRecordsCountUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {

    suspend operator fun invoke(date: String) : Int {
        return calendarRepository.getDailyRecords(date).getOrNull()?.records?.size ?: 0
    }
}
