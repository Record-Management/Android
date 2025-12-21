package see.day.notification.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object TimeFormatUtil {
    fun getRelativeTimeString(dateTimeString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        val now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

        val duration = Duration.between(dateTime, now)
        val hours = duration.toHours()
        val days = duration.toDays()

        return when {
            days >= 1 -> "${days}일 전"
            hours >= 0 -> "${hours}시간 전"
            else -> {
                "0시간 전"
            }
        }
    }

    fun hourBefore(hour: Long): String {
        val now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

        val hoursBefore = now.minusHours(hour)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")

        return hoursBefore.format(formatter)
    }

    fun daysBefore(days: Long): String {
        val now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

        val daysBefore = now.minusDays(days)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")

        return daysBefore.format(formatter)
    }

}
