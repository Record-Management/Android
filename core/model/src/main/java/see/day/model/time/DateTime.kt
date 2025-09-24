package see.day.model.time

import java.time.ZoneId
import java.time.ZonedDateTime

data class DateTime(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int
) {
    companion object {
        /**
         * 특정 시간대의 현재 시간으로 DateTime 생성
         */
        val korea = "Asia/Seoul"

        fun now(zoneId: String): DateTime {
            val zonedTime = ZonedDateTime.now(ZoneId.of(zoneId))
            return DateTime(
                year = zonedTime.year,
                month = zonedTime.monthValue,
                day = zonedTime.dayOfMonth,
                hour = zonedTime.hour,
                minute = zonedTime.minute
            )
        }

        /**
         * 특정 날짜/시간으로 DateTime 생성
         */
        fun of(year: Int, month: Int, day: Int, hour: Int, minute: Int): DateTime {
            return DateTime(year, month, day, hour, minute)
        }
    }
}
