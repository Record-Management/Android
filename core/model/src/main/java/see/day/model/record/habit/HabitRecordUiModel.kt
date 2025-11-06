package see.day.model.record.habit

data class HabitRecordUiModel(
    val habitType: HabitType,
    val notificationEnabled: Boolean,
    val notificationTime: String?,
    val memo: String?,
    val isMainRecord: Boolean,
    val canBeMain: Boolean
)
