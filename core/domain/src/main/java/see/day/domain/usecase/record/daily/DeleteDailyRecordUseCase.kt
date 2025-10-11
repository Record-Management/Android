package see.day.domain.usecase.record.daily

import see.day.domain.repository.DailyRecordRepository
import javax.inject.Inject

class DeleteDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) {

    suspend operator fun invoke(recordId: String) : Result<Unit> {
        return dailyRecordRepository.deleteRecord(recordId)
    }
}
