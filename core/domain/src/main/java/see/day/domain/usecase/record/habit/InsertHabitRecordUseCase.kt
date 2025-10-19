package see.day.domain.usecase.record.habit

import see.day.domain.repository.HabitRecordRepository
import see.day.model.calendar.HabitRecordDetail
import see.day.model.record.habit.HabitRecordInput
import javax.inject.Inject

class InsertHabitRecordUseCase @Inject constructor(
    private val habitRecordRepository: HabitRecordRepository
){

    suspend operator fun invoke(habitRecordInput: HabitRecordInput) : Result<HabitRecordDetail> {
        return habitRecordRepository.insertHabitRecord(habitRecordInput)
    }
}
