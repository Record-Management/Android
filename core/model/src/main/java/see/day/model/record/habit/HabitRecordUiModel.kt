package see.day.model.record.habit

data class HabitRecordUiModel(
    val id: String,
    val habitType: HabitType,
    val notificationEnabled: Boolean,
    val notificationHour: Int,
    val notificationMinute: Int,
    val memo: String,
    val isMainRecord: Boolean,
    val canBeMain: Boolean
)
