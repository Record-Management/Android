package see.day.domain.repository

import see.day.model.calendar.HabitRecordDetail
import see.day.model.record.habit.HabitRecordInput

interface HabitRecordRepository {

    suspend fun insertHabitRecord(habitRecordInput: HabitRecordInput): Result<HabitRecordDetail>
}
