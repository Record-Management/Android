package see.day.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

internal fun convertTo12HourFormat(timeString24Hour: String): String {
    try {
        val inputFormatter = DateTimeFormatter.ofPattern("HH:mm")

        val localTime = LocalTime.parse(timeString24Hour, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.US)

        return outputFormatter.format(localTime)
    } catch (e: Exception) {
        return timeString24Hour
    }
}
