package see.day.domain.usecase.record.daily

import see.day.domain.repository.DailyRecordRepository
import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.RegisteredDailyRecord
import javax.inject.Inject

class InsertDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
){

    suspend operator fun invoke(createDailyRecord: CreateDailyRecord) : Result<RegisteredDailyRecord> {
        return dailyRecordRepository.insertRecord(createDailyRecord)
    }
}
