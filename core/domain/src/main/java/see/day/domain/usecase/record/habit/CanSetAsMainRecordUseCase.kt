package see.day.domain.usecase.record.habit

import see.day.domain.repository.CalendarRepository
import see.day.domain.repository.UserRepository
import see.day.model.record.RecordType
import javax.inject.Inject

class CanSetAsMainRecordUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val calendarRepository: CalendarRepository
) {

    suspend operator fun invoke(date: String) : Result<Boolean>{
        val mainRecordType = userRepository.getMainRecordType().getOrElse {
            return Result.failure(it)
        }

        if(mainRecordType != RecordType.HABIT) {
            return Result.success(false)
        }

        val dailyRecords = calendarRepository.getDailyRecords(date).getOrElse {
            return Result.failure(it)
        }.records

        return if(dailyRecords.any { it.type == RecordType.HABIT }) {
            Result.success(true)
        } else {
            Result.success(false)
        }
    }
}
