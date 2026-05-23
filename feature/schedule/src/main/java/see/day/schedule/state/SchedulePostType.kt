package see.day.schedule.state

sealed interface SchedulePostType {
    data class Edit(val id: String) : SchedulePostType
    data object Write : SchedulePostType
}
