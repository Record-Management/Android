package see.day.domain.usecase.record.daily

import see.day.domain.repository.DailyRecordRepository
import see.day.model.record.daily.DailyRecordDetail
import see.day.model.record.daily.ModifyDailyRecord
import javax.inject.Inject

class PutDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) {

    suspend operator fun invoke(modifyDailyRecord: ModifyDailyRecord) : Result<DailyRecordDetail> {
        return dailyRecordRepository.putRecord(modifyDailyRecord)
    }
}
