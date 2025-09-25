package see.day.model.time

interface DateTimeFormatter {
    fun formatFullDate(): String
    fun formatFullTime(): String
    fun formatDate(): String
    fun formatTime(): String
}
