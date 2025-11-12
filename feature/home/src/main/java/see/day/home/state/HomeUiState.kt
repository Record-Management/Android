package see.day.home.state

import java.time.LocalDate
import see.day.home.util.RecordFilterType
import see.day.model.calendar.DailyRecordDetails
import see.day.model.date.CalendarDayInfo
import see.day.model.goal.TreeStage
import see.day.model.record.RecordType

data class HomeUiState(
    val currentYear: Int,
    val currentMonth: Int,
    val selectedMonth: Int,
    val selectedDay: Int,
    val selectedFilterType: RecordFilterType,
    val mainRecordType: RecordType,
    val goalDays: Int,
    val monthlyRecords: List<CalendarDayInfo>,
    val dailyRecordDetails: DailyRecordDetails,
    val createdAt: String,
    val todayRecords: DailyRecordDetails,
    val treeStage: TreeStage?,
    val shouldCreateNewGoal : Boolean
) {
    companion object {
        val init = HomeUiState(
            currentYear = LocalDate.now().year,
            currentMonth = LocalDate.now().monthValue,
            selectedMonth = LocalDate.now().monthValue,
            selectedDay = LocalDate.now().dayOfMonth,
            selectedFilterType = RecordFilterType.ALL,
            mainRecordType = RecordType.DAILY,
            goalDays = 10,
            monthlyRecords = listOf(),
            dailyRecordDetails = DailyRecordDetails(getTodayDate(), listOf()),
            createdAt = getTodayDate(),
            todayRecords = DailyRecordDetails(getTodayDate(), listOf()),
            treeStage = TreeStage.STAGE_1,
            shouldCreateNewGoal = false
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
