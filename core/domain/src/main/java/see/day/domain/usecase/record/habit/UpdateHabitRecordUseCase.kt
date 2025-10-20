package see.day.domain.usecase.record.habit

import see.day.domain.repository.HabitRecordRepository
import see.day.model.calendar.HabitRecordDetail
import see.day.model.record.habit.HabitRecordEdit
import javax.inject.Inject

class UpdateHabitRecordUseCase @Inject constructor(
    private val habitRecordRepository: HabitRecordRepository
){

    suspend operator fun invoke(recordId: String, habitRecordEdit: HabitRecordEdit) : Result<HabitRecordDetail> {
        return habitRecordRepository.updateHabitRecord(recordId, habitRecordEdit)
    }

}
