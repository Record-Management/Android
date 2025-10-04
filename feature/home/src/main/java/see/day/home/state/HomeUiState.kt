package see.day.home.state

import java.time.LocalDate
import see.day.home.util.RecordFilterType
import see.day.model.calendar.DailyDetailRecords
import see.day.model.date.CalendarDayInfo
import see.day.model.record.RecordType

data class HomeUiState(
    val currentYear: Int,
    val currentMonth: Int,
    val selectedMonth: Int,
    val selectedDay: Int,
    val selectedFilterType: RecordFilterType,
    val mainRecordType: RecordType,
    val monthlyRecords: List<CalendarDayInfo>,
    val dailyDetailRecords: DailyDetailRecords
) {
    companion object {
        val init = HomeUiState(
            currentYear = LocalDate.now().year,
            currentMonth = LocalDate.now().monthValue,
            selectedMonth = LocalDate.now().monthValue,
            selectedDay = LocalDate.now().dayOfMonth,
            selectedFilterType = RecordFilterType.ALL,
            mainRecordType = RecordType.DAILY,
            monthlyRecords = listOf(),
            dailyDetailRecords = DailyDetailRecords(getTodayDate(), listOf())
        )

        fun getTodayDate(): String {
            val now = LocalDate.now()
            return "${now.year}-${now.monthValue.toString().padStart(2, '0')}-${now.dayOfMonth.toString().padStart(2, '0')}"
        }
    }

    fun todayFormat(): String {
        return "${currentYear}-${selectedMonth.toString().padStart(2, '0')}-${selectedDay.toString().padStart(2, '0')}"
    }
}
