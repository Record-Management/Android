package see.day.model.calendar

import see.day.model.record.RecordType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class DailyDetailRecord(
    val date: String,
    val records: List<DetailRecord>
) {
    val formatFullDate : String by lazy {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(date, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.KOREAN)
        localDate.format(outputFormatter)
    }
}

data class DetailRecord(
    val id: String,
    val type: RecordType,
    val emotion: String,
    val content: String,
    val imageUrls: List<String>,
    val recordDate: String,
    val recordTime: String,
    val createdAt: String,
    val updatedAt: String
) {
    val fullRecordTime : String by lazy {
        val inputFormatter = DateTimeFormatter.ofPattern("H:m")
        val localTime = LocalTime.parse(recordTime, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN)
        localTime.format(outputFormatter)
    }
}
