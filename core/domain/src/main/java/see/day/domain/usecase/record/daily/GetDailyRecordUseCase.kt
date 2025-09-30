package see.day.domain.usecase.record.daily

import see.day.domain.repository.RecordRepository
import see.day.model.record.daily.DailyRecordDetail
import javax.inject.Inject

class GetDailyRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
){

    suspend operator fun invoke(recordId : String) : Result<DailyRecordDetail> {
        return recordRepository.getRecord(recordId)
    }
}
