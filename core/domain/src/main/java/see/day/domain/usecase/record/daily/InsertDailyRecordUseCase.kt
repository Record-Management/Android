package see.day.domain.usecase.record.daily

import see.day.domain.repository.DailyRecordRepository
import see.day.model.record.daily.DailyRecordInput
import see.day.model.record.daily.DailyRecordDetail
import javax.inject.Inject

class InsertDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
){

    suspend operator fun invoke(dailyRecordInput: DailyRecordInput) : Result<DailyRecordDetail> {
        return dailyRecordRepository.insertRecord(dailyRecordInput)
    }
}
