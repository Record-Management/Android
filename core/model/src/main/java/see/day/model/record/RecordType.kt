package see.day.model.record

enum class RecordType(
    val title: String,
    val isAvailableForGoal: Boolean = true,
    val isAvailableForMainDisplay: Boolean = true
) {
    DAILY("하루 기록"),
    EXERCISE("운동 기록"),
    HABIT("습관 기록"),
    SCHEDULE("일정", isAvailableForGoal = false, isAvailableForMainDisplay = false);

    companion object {
        fun getGoalAvailableTypes(): List<RecordType> {
            return entries.filter { it.isAvailableForGoal }
        }

        fun getMainDisplayAvailableTypes(): List<RecordType> {
            return entries.filter { it.isAvailableForMainDisplay }
        }

        fun getCalendarAvailableTypes(): List<RecordType> {
            return entries.toList()
        }
    }
}
