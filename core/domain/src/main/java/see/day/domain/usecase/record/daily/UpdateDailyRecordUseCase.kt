package see.day.domain.usecase.record.daily

import see.day.domain.repository.DailyRecordRepository
import see.day.model.calendar.DailyRecordDetail
import see.day.model.record.daily.DailyRecordEdit
import javax.inject.Inject

class UpdateDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) {

    suspend operator fun invoke(dailyRecordEdit: DailyRecordEdit) : Result<DailyRecordDetail> {
        return dailyRecordRepository.updateRecord(dailyRecordEdit)
    }
}
