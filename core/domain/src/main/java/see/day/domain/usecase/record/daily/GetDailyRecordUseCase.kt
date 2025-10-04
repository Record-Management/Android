package see.day.domain.usecase.record.daily

import see.day.domain.repository.RecordRepository
import see.day.model.calendar.RecordDetail
import javax.inject.Inject

class GetDailyRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
){

    suspend operator fun invoke(recordId : String) : Result<RecordDetail> {
        return recordRepository.getRecord(recordId)
    }
}
