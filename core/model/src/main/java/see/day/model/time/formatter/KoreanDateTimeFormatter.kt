package see.day.model.time.formatter

import see.day.model.time.DateTime
import see.day.model.time.DateTimeFormatter
import java.time.ZoneId
import java.time.ZonedDateTime

class KoreanDateTimeFormatter(val dateTime: DateTime) : DateTimeFormatter {

    private fun getDayOfWeekKorean(): String {
        val zonedDateTime = ZonedDateTime.of(
            dateTime.year, dateTime.month, dateTime.day,
            dateTime.hour, dateTime.minute, 0, 0,
            ZoneId.of("Asia/Seoul")
        )
        return when(zonedDateTime.dayOfWeek.value) {
            1 -> "월"
            2 -> "화"
            3 -> "수"
            4 -> "목"
            5 -> "금"
            6 -> "토"
            7 -> "일"
            else -> ""
        }
    }

    override fun formatFullDate(): String {
        val yearStr = (dateTime.year % 100).toString().padStart(2, '0')
        val monthStr = dateTime.month.toString().padStart(2, '0')
        val dayStr = dateTime.day.toString().padStart(2, '0')
        val dayOfWeek = getDayOfWeekKorean()

        return "$yearStr.$monthStr.$dayStr ($dayOfWeek)"
    }

    override fun formatFullTime(): String {
        val amPm = if (dateTime.hour < 12) "오전" else "오후"
        val displayHour = when {
            dateTime.hour == 0 -> 12
            dateTime.hour > 12 -> dateTime.hour - 12
            else -> dateTime.hour
        }
        val hourStr = displayHour.toString().padStart(2, '0')
        val minuteStr = dateTime.minute.toString().padStart(2, '0')

        return "$amPm $hourStr:$minuteStr"
    }

    override fun formatDate(): String {
        val yearStr = dateTime.year.toString()
        val monthStr = dateTime.month.toString().padStart(2, '0')
        val dayStr = dateTime.day.toString().padStart(2, '0')

        return "$yearStr-$monthStr-$dayStr"
    }

    override fun formatTime(): String {
        val hourStr = dateTime.hour.toString().padStart(2, '0')
        val minuteStr = dateTime.minute.toString().padStart(2, '0')

        return "$hourStr:$minuteStr"
    }
}
