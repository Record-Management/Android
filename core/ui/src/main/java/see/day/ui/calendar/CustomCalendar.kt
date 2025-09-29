package see.day.ui.calendar

import android.widget.Toast
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.date.CalendarDayInfo
import see.day.model.date.generateCalendarDays
import see.day.model.record.RecordType

@Composable
fun CustomCalendar(
    modifier: Modifier = Modifier,
    currentYear: Int,
    currentMonth: Int,
    selectedMonth: Int,
    selectedDay: Int,
    calendarDayInfo: List<CalendarDayInfo>,
    currentFilterType: RecordType?,
    mainRecordType: RecordType,
    onClickCell: (Int, Int, Int) -> Unit,
    onSwipeCalendar: (Int, Int) -> Unit
) {
    val generateCalendarDay by remember(currentYear, currentMonth) {
        mutableStateOf(
            generateCalendarDays(currentYear, currentMonth)
        )
    }

    Column(
        modifier = modifier
            .pointerInput(currentYear, currentMonth) {
                var totalDragAmount = 0f
                detectHorizontalDragGestures(
                    onDragStart = {
                        totalDragAmount = 0f
                    },
                    onDragEnd = {
                        if (abs(totalDragAmount) > 100) {
                            if (totalDragAmount > 0) {
                                // 이전 달
                                if (currentMonth == 1) {
                                    onSwipeCalendar(currentYear - 1, 12)
                                } else {
                                    onSwipeCalendar(currentYear, currentMonth - 1)
                                }
                            } else {
                                // 다음 달
                                if (currentMonth == 12) {
                                    onSwipeCalendar(currentYear + 1, 1)
                                } else {
                                    onSwipeCalendar(currentYear, currentMonth + 1)
                                }
                            }
                        }
                        totalDragAmount = 0f // 여기서도 한번 더 초기화
                    }
                ) { _, dragAmount ->
                    totalDragAmount += dragAmount
                }
            }
    ) {
        // 요일 헤더
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            dayOfWeekList.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 9.dp),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        // 캘린더 날짜들을 주 단위로 나누어 표시
        generateCalendarDay.chunked(7).forEach { weekData ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                weekData.forEach { date ->
                    DayCell(
                        modifier = modifier.weight(1f),
                        year = date.year,
                        month = date.month,
                        day = date.day,
                        isSameMonth = date.isCurrentMonth,
                        filterType = currentFilterType,
                        isSelected = currentYear == date.year && selectedDay == date.day && selectedMonth == date.month,
                        mainRecordType = mainRecordType,
                        records = calendarDayInfo.firstOrNull {
                            it.day == date.day && it.month == date.month && it.year == date.year
                        }?.records ?: listOf(),
                        schedules = calendarDayInfo.firstOrNull {
                            it.day == date.day && it.month == date.month && it.year == date.year
                        }?.schedules ?: listOf(),
                        onClickItem = onClickCell
                    )
                }
            }
        }
    }
}

val dayOfWeekList = listOf("일", "월", "화", "수", "목", "금", "토")

@Preview
@Composable
private fun CustomCalendarPreview() {
    val context = LocalContext.current
    var currentYear by remember { mutableStateOf(2025) }
    var currentMonth by remember { mutableStateOf(9) }
    SeeDayTheme {
        CustomCalendar(
            currentYear = currentYear,
            currentMonth = currentMonth,
            selectedMonth = 9,
            selectedDay = 15,
            onClickCell = { year, month, day ->
                Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
            },
            onSwipeCalendar = { year, month ->
                Toast.makeText(context, "$year $month", Toast.LENGTH_SHORT).show()
                currentYear = year
                currentMonth = month
            },
            calendarDayInfo = listOf(),
            currentFilterType = null,
            mainRecordType = RecordType.EXERCISE
        )
    }
}
