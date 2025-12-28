package see.day.ui.calendar

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.R
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray40
import see.day.model.calendar.DailyRecord
import see.day.model.record.RecordType
import see.day.model.record.RecordType.DAILY
import see.day.model.record.RecordType.EXERCISE
import see.day.model.record.RecordType.HABIT
import see.day.util.getGrayIcon
import see.day.util.getIcon
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DayCell(modifier: Modifier = Modifier, isSameMonth: Boolean = true, isSelected: Boolean = false, year: Int, month: Int, day: Int, filterType: RecordType?, mainRecordType: RecordType?, records: List<DailyRecord>, schedules: List<String>, createdAt: String, onClickItem: (Int, Int, Int) -> Unit) {
    Column(
        modifier = modifier
            .heightIn(min = 80.dp)
            .fillMaxWidth()
            .clickable { onClickItem(year, month, day) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.toString(),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = if (isSelected) Color.White else if (isSameMonth) gray100 else gray40,
            modifier = if (isSelected) {
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(MaterialTheme.colorScheme.primary)
            } else {
                Modifier.fillMaxWidth()
            }
        )
        if (!isSameMonth || isAfterToday(year, month, day) || isBeforeCreatedAt(createdAt, year, month, day)) {
            return
        }
        if (isBeforeToday(year, month, day)) {
            PastDayImages(filterType, mainRecordType, records)
        } else if (LocalDate.of(year, month, day).isEqual(now)) {
            TodayImages(filterType, mainRecordType, records)
        }
    }
}

@Composable
private fun PastDayImages(filterType: RecordType?, mainRecordType: RecordType?, records: List<DailyRecord>) {
    if (records.isEmpty()) {
        return
    }
    if (filterType == null) {
        if (mainRecordType == null) {
            return
        }
        Box(
            modifier = Modifier
                .height(25.dp)
                .padding(horizontal = (5.5).dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 1.dp)
                    .align(Alignment.Center)
            ) {
                // 동적으로 이미지 변경, 이미지 색상 변경(회색, 그냥 원래 색)
                Icon(
                    painter = painterResource(
                        if (mainRecordType == HABIT) {
                            if (records.filter { it.type == HABIT }.any { it.isCompleted }) {
                                mainRecordType.getIcon()
                            } else {
                                mainRecordType.getGrayIcon()
                            }
                        } else {
                            if(records.any { it.type == mainRecordType }) {
                                mainRecordType.getIcon()
                            } else {
                                mainRecordType.getGrayIcon()
                            }
                        }
                    ),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "이미지",
                    tint = Color.Unspecified
                )
            }
            // 점이 찍히는 조건
            if (records.size >= 2 || records.any { it.type != mainRecordType }) {
                Icon(
                    painter = painterResource(R.drawable.ic_red_dot),
                    modifier = Modifier
                        .size(6.dp)
                        .align(Alignment.TopCenter)
                        .offset(x = (14.5).dp),
                    contentDescription = "선택된 버튼",
                    tint = Color.Unspecified
                )
            }
        }
        return
    }
    val mainTypeRecords = records.filter { it.type == filterType }
    Box(
        modifier = Modifier
            .height(25.dp)
            .padding(horizontal = (5.5).dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 1.dp)
                .align(Alignment.Center)
        ) {
            // 동적으로 이미지 변경, 이미지 색상 변경(회색, 그냥 원래 색)
            if (mainTypeRecords.isNotEmpty()) {
                Icon(
                    painter = painterResource(filterType.getIcon()),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "이미지",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
private fun TodayImages(filterType: RecordType?, mainRecordType: RecordType?, records: List<DailyRecord>) {
    if (records.isEmpty()) {
        return
    }
    if (filterType == null) {
        if (mainRecordType == null) {
            return
        }
        Box(
            modifier = Modifier
                .height(25.dp)
                .padding(horizontal = (5.5).dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 1.dp)
                    .align(Alignment.Center)
            ) {
                // 동적으로 이미지 변경, 이미지 색상 변경(회색, 그냥 원래 색)
                if (records.any { it.type == mainRecordType }) {
                    Icon(
                        painter = painterResource(
                            if (mainRecordType == HABIT) {
                                if (records.filter { it.type == HABIT }.any { it.isCompleted }) {
                                    mainRecordType.getIcon()
                                } else {
                                    mainRecordType.getGrayIcon()
                                }
                            } else {
                                mainRecordType.getIcon()
                            }
                        ),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "이미지",
                        tint = Color.Unspecified
                    )
                } else if(records.any { it.type != mainRecordType }) {
                    Icon(
                        painter = painterResource(mainRecordType.getGrayIcon()),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "이미지",
                        tint = Color.Unspecified
                    )
                }
            }
            // 점이 찍히는 조건
            if (records.size >= 2 || records.any { it.type != mainRecordType }) {
                Icon(
                    painter = painterResource(R.drawable.ic_red_dot),
                    modifier = Modifier
                        .size(6.dp)
                        .align(Alignment.TopCenter)
                        .offset(x = (14.5).dp),
                    contentDescription = "선택된 버튼",
                    tint = Color.Unspecified
                )
            }
        }
        return
    }
    val mainTypeRecords = records.filter { it.type == filterType }
    Box(
        modifier = Modifier
            .height(25.dp)
            .padding(horizontal = (5.5).dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 1.dp)
                .align(Alignment.Center)
        ) {
            // 동적으로 이미지 변경, 이미지 색상 변경(회색, 그냥 원래 색)
            if (mainTypeRecords.isNotEmpty()) {
                Icon(
                    painter = painterResource(filterType.getIcon()),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "이미지",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

private fun isBeforeToday(year: Int, month: Int, day: Int): Boolean {
    val inputDate = LocalDate.of(year, month, day)
    return inputDate.isBefore(now)
}

private fun isAfterToday(year: Int, month: Int, day: Int): Boolean {
    val inputDate = LocalDate.of(year, month, day)
    return inputDate.isAfter(now)
}

private fun isBeforeCreatedAt(createdAt: String, year: Int, month: Int, day: Int): Boolean {
    val inputDate = LocalDate.of(year, month, day)
    val createdArray = createdAt.substringBefore(":").split("-").map { it.toInt() }
    return inputDate.isBefore(LocalDate.of(createdArray[0], createdArray[1], createdArray[2]))
}

private val now = LocalDate.now(ZoneId.of("Asia/Seoul"))

// 두 개 이상인 경우
@Preview
@Composable
private fun PastDayCellFilterAllAndTwoDailyRecord() {
    val date = now.minusDays(3)
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = date.year,
                month = date.monthValue,
                day = date.dayOfMonth,
                filterType = null,
                isSelected = true,
                mainRecordType = DAILY,
                records = listOf(DailyRecord(id = "", DAILY, true), DailyRecord(id = "", HABIT, isCompleted = true)),
                schedules = listOf(),
                createdAt = "2025-10-10",
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true
            )
        }
    }
}

@Preview
@Composable
private fun PastDayCellFilterAllAndHabitComplete() {
    val date = now.minusDays(3)
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = date.year,
                month = date.monthValue,
                day = date.dayOfMonth,
                filterType = null,
                isSelected = true,
                mainRecordType = HABIT,
                records = listOf(DailyRecord(id = "", DAILY, true), DailyRecord(id = "", HABIT, isCompleted = true)),
                schedules = listOf(),
                createdAt = "2025-10-10",
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true
            )
        }
    }
}

@Preview
@Composable
private fun PastDayCellFilterAllAndHabitNotComplete() {
    val date = now.minusDays(3)
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = date.year,
                month = date.monthValue,
                day = date.dayOfMonth,
                filterType = null,
                isSelected = true,
                mainRecordType = HABIT,
                records = listOf(DailyRecord(id = "", HABIT, isCompleted = false)),
                schedules = listOf(),
                createdAt = "2025-10-10",
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true
            )
        }
    }
}

@Preview
@Composable
private fun PastDayCellFilterDailyAndHasDailyRecord() {
    val date = now.minusDays(3)
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = date.year,
                month = date.monthValue,
                day = date.dayOfMonth,
                filterType = DAILY,
                isSelected = true,
                mainRecordType = HABIT,
                records = listOf(DailyRecord(id = "", DAILY, isCompleted = false)),
                schedules = listOf(),
                createdAt = "2025-10-10",
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true
            )
        }
    }
}

@Preview
@Composable
private fun PastDayCellFilterDailyAndHasNoDailyRecord() {
    val date = now.minusDays(3)
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = date.year,
                month = date.monthValue,
                day = date.dayOfMonth,
                filterType = DAILY,
                isSelected = true,
                mainRecordType = HABIT,
                records = listOf(DailyRecord(id = "", EXERCISE, isCompleted = false)),
                schedules = listOf(),
                createdAt = "2025-10-10",
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true
            )
        }
    }
}

@Preview
@Composable
private fun TodayCellFilterAllAndHabitComplete() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = now.year,
                month = now.monthValue,
                day = now.dayOfMonth,
                filterType = null,
                isSelected = true,
                mainRecordType = HABIT,
                records = listOf(DailyRecord(id = "", DAILY, true), DailyRecord(id = "", HABIT, isCompleted = true)),
                schedules = listOf(),
                createdAt = "2025-10-10",
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true
            )
        }
    }
}

@Preview
@Composable
private fun TodayCellFilterAllAndHabitNotComplete() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = now.year,
                month = now.monthValue,
                day = now.dayOfMonth,
                filterType = null,
                isSelected = true,
                mainRecordType = HABIT,
                records = listOf(DailyRecord(id = "", HABIT, isCompleted = false)),
                schedules = listOf(),
                createdAt = "2025-10-10",
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true
            )
        }
    }
}

@Preview
@Composable
private fun TodayCellFilterAllAndNoHabit() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = now.year,
                month = now.monthValue,
                day = now.dayOfMonth,
                filterType = null,
                isSelected = true,
                mainRecordType = HABIT,
                records = listOf(),
                schedules = listOf(),
                createdAt = "2025-10-10",
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true
            )
        }
    }
}
