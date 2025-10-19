package see.day.habit.state

import see.day.model.record.habit.HabitType

sealed interface HabitRecordPostType {
    data class Edit(val id: String) : HabitRecordPostType
    data class Write(val habitType: HabitType) : HabitRecordPostType
}
