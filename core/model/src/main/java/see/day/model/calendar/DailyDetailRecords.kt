package see.day.model.calendar

import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class DailyDetailRecords(
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
    abstract val createdAt: String
    abstract val updatedAt: String
}

data class DailyRecordDetail(
    override val id: String,
    override val type: RecordType,
    override val recordDate: String,
    override val createdAt: String,
    override val updatedAt: String,
    val emotion: DailyEmotion,
    val content: String,
    val imageUrls: List<String>,
    val recordTime: String
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
    val recordTime: String?,
    val imageUrls: List<String>,
    val exerciseTimeMinutes: Int,
    val stepCount: Int,
    val weight: Float,
    val dailyNote: String
) : RecordDetail()
