package see.day.model.time.formatter

import java.time.LocalDate
import java.time.LocalTime

fun LocalDate.formatIsoDate(): String {
    return "%04d-%02d-%02d".format(year, monthValue, dayOfMonth)
}

fun LocalTime.formatHourMinute(): String {
    return "%02d:%02d".format(hour, minute)
}
