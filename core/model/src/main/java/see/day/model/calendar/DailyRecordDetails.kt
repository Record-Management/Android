package see.day.model.calendar

import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion
import see.day.model.record.exercise.ExerciseType
import see.day.model.record.habit.HabitType
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class DailyRecordDetails(
    val date: String,
    val records: List<RecordDetail>
) {
    val formatFullDate: String by lazy {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(date, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.KOREAN)
        localDate.format(outputFormatter)
    }
}

sealed class RecordDetail {
    abstract val id: String
    abstract val type: RecordType
    abstract val recordDate: String
    abstract val recordTime: String
    abstract val createdAt: String
    abstract val updatedAt: String
}

data class DailyRecordDetail(
    override val id: String,
    override val type: RecordType,
    override val recordDate: String,
    override val createdAt: String,
    override val updatedAt: String,
    override val recordTime: String,
    val emotion: DailyEmotion,
    val content: String,
    val imageUrls: List<String>,
) : RecordDetail() {
    val fullRecordTime: String by lazy {
        val inputFormatter = DateTimeFormatter.ofPattern("H:m")
        val localTime = LocalTime.parse(recordTime, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN)
        localTime.format(outputFormatter)
    }
}

data class ExerciseRecordDetail(
    override val id: String,
    override val type: RecordType,
    override val recordDate: String,
    override val createdAt: String,
    override val updatedAt: String,
    override val recordTime: String,
    val exerciseType: ExerciseType,
    val imageUrls: List<String>,
    val exerciseTimeMinutes: String,
    val stepCount: String,
    val weight: String,
    val caloriesBurned: String,
    val dailyNote: String
) : RecordDetail() {
    fun formatTimeTo12Hour(): String {
        val parts = recordTime.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1]

        return when {
            hour == 0 -> "오전 12:$minute"
            hour < 12 -> "오전 $hour:$minute"
            hour == 12 -> "오후 12:$minute"
            else -> "오후 ${hour - 12}:$minute"
        }
    }
}

data class HabitRecordDetail(
    override val id: String,
    override val type: RecordType,
    override val recordDate: String,
    override val createdAt: String,
    override val updatedAt: String,
    override val recordTime: String,
    val habitType: HabitType,
    val notificationEnabled: Boolean,
    val notificationTime: String,
    val memo: String,
    val isCompleted: Boolean
) : RecordDetail()
