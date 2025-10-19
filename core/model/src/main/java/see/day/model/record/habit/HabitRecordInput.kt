package see.day.model.record.habit

data class HabitRecordInput(
    val habitType: HabitType,
    val notificationEnabled: Boolean,
    val notificationHour: Int,
    val notificationMinute: Int,
    val memo: String,
    val recordDate: String
)
