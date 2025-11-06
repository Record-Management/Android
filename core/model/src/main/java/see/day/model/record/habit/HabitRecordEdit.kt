package see.day.model.record.habit

data class HabitRecordEdit(
    val habitType: HabitType,
    val notificationEnabled: Boolean,
    val hour: Int,
    val minute: Int,
    val memo: String,
    val isMainRecord: Boolean
)
