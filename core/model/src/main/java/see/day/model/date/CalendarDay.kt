package see.day.model.date

import java.time.LocalDate

data class CalendarDay(
    val day: Int,
    val month: Int,
    val year: Int,
    val isCurrentMonth: Boolean
)

fun generateCalendarDays(year: Int, month: Int): List<CalendarDay> {
    val firstDay = LocalDate.of(year, month, 1)

    // 일요일 기준 (일요일: 0, 월요일: 1, ..., 토요일: 6)
    val dayOfWeek = firstDay.dayOfWeek.value % 7  // 일요일을 0으로 변환
    val firstCalendarDate = 1 - dayOfWeek

    val calendarDays = mutableListOf<CalendarDay>()

    for (i in 0 until 35) { // 5주 * 7일
        val currentDate = firstDay.plusDays((firstCalendarDate - 1 + i).toLong())

        calendarDays.add(
            CalendarDay(
                day = currentDate.dayOfMonth,
                month = currentDate.monthValue,
                year = currentDate.year,
                isCurrentMonth = currentDate.monthValue == month
            )
        )
    }

    return calendarDays
}
