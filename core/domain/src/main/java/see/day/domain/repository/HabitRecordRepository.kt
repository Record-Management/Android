package see.day.domain.repository

import see.day.model.calendar.HabitRecordDetail
import see.day.model.record.habit.HabitRecordEdit
import see.day.model.record.habit.HabitRecordInput

interface HabitRecordRepository {

    suspend fun insertHabitRecord(habitRecordInput: HabitRecordInput): Result<HabitRecordDetail>

    suspend fun deleteHabitRecord(recordId: String) : Result<Unit>

    suspend fun updateHabitRecordIsCompleted(recordId: String,isCompleted: Boolean) : Result<Boolean>

    suspend fun updateHabitRecord(recordId: String, habitRecordEdit: HabitRecordEdit) : Result<HabitRecordDetail>
}
