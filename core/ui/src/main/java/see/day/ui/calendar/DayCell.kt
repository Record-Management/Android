package see.day.ui.calendar

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.R
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray40
import see.day.designsystem.theme.gray50
import see.day.model.record.RecordType
import see.day.model.record.RecordType.DAILY
import see.day.model.record.RecordType.HABIT
import see.day.model.record.RecordType.SCHEDULE
import see.day.util.getGrayIcon
import see.day.util.getIcon

@Composable
fun DayCell(
    modifier: Modifier = Modifier,
    isSameMonth: Boolean = true,
    isSelected: Boolean = false,
    year: Int,
    month: Int,
    day: Int,
    filterType: RecordType?,
    mainRecordType: RecordType,
    records: List<RecordType>,
    schedules: List<String>,
    onClickItem: (Int, Int, Int) -> Unit
) {
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
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(MaterialTheme.colorScheme.primary)
            } else {
                modifier.fillMaxWidth()
            }
        )
        if (!isSameMonth) {
            return
        }
        // 아이콘이 정상적인 색상으로 나오는 것
        if(mainRecordType != SCHEDULE) {
            if(filterType == null && records.contains(mainRecordType)) {
                Box(
                    modifier = modifier
                        .height(25.dp)
                        .padding(horizontal = (5.5).dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = modifier
                            .padding(top = 1.dp)
                            .align(Alignment.Center)
                    ) {
                        // 동적으로 이미지 변경, 이미지 색상 변경(회색, 그냥 원래 색)
                        Image(
                            painter = painterResource(mainRecordType.getIcon()),
                            modifier = modifier.size(24.dp),
                            contentDescription = "이미지",
                        )
                    }
                    // 점이 찍히는 조건
                    if (records.size >= 2) {
                        Image(
                            painter = painterResource(R.drawable.ic_red_dot),
                            modifier = modifier
                                .size(6.dp)
                                .align(Alignment.TopCenter)
                                .offset(x = (14.5).dp),
                            contentDescription = "선택된 버튼",
                        )
                    }
                }
            } else if(filterType == null && !records.contains(mainRecordType)) {
                Box(
                    modifier = modifier
                        .height(25.dp)
                        .padding(horizontal = (5.5).dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = modifier
                            .padding(top = 1.dp)
                            .align(Alignment.Center)
                    ) {
                        // 동적으로 이미지 변경, 이미지 색상 변경(회색, 그냥 원래 색)
                        Image(
                            painter = painterResource(mainRecordType.getGrayIcon()),
                            modifier = modifier.size(24.dp),
                            contentDescription = "이미지",
                        )
                    }
                    // 점이 찍히는 조건
                    if (records.isNotEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.ic_red_dot),
                            modifier = modifier
                                .size(6.dp)
                                .align(Alignment.TopCenter)
                                .offset(x = (14.5).dp),
                            contentDescription = "선택된 버튼",
                        )
                    }
                }
            } else if(filterType != null && records.contains(filterType)) {
                Box(
                    modifier = modifier
                        .height(25.dp)
                        .padding(horizontal = (5.5).dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = modifier
                            .padding(top = 1.dp)
                            .align(Alignment.Center)
                    ) {
                        // 동적으로 이미지 변경, 이미지 색상 변경(회색, 그냥 원래 색)
                        Image(
                            painter = painterResource(filterType.getIcon()),
                            modifier = modifier.size(24.dp),
                            contentDescription = "이미지",
                        )
                    }
                }
            }
        }

        if (schedules.isNotEmpty() && (filterType == null || filterType == SCHEDULE)) {
            Spacer(modifier = modifier.size(6.dp))
            Row(
                modifier = modifier
                    .clip(RoundedCornerShape(2.dp))
                    .background(gray20)
                    .fillMaxWidth()
                    .padding(end = 4.dp)
                    .align(Alignment.Start),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                // 색상
                Spacer(
                    modifier = modifier
                        .padding(2.dp)
                        .size(width = 2.dp, height = 10.dp)
                        .background(gray50)
                )
                Text(
                    text = schedules[0],
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.padding(bottom = 1.dp)
                )
            }
            if (schedules.size >= 2) {
                Text(
                    text = "+${schedules.size - 1}",
                    modifier = modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(gray20)
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

// 두 개 이상인 경우
@Preview
@Composable
private fun DayCellFilterTypeNullDoubleRecordsNoSchedulePreview() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = 2025,
                month = 9,
                day = 13,
                filterType = null,
                isSelected = true,
                mainRecordType = DAILY,
                records = listOf(DAILY, RecordType.HABIT),
                schedules = listOf(),
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true,
            )
        }
    }
}

// 타입이 ALL이고 본인에 해당하는 타입이 없으며 records가 한 개 존재하는경우
@Preview
@Composable
private fun DayCellFilterTypeNullNoRecordNoSchedulePreview() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = 2025,
                month = 9,
                day = 13,
                filterType = null,
                isSelected = true,
                mainRecordType = DAILY,
                records = listOf(RecordType.HABIT),
                schedules = listOf("hello"),
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true,
            )
        }
    }
}

// 타입이 Daily이고 본인에 해당하는 타입이 없으며 records가 여러 개 존재하는경우
@Preview
@Composable
private fun DayCellFilterTypeDailyThreeRecordNoSchedulePreview() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = 2025,
                month = 9,
                day = 13,
                filterType = DAILY,
                isSelected = true,
                mainRecordType = DAILY,
                records = listOf(DAILY, DAILY, HABIT),
                schedules = listOf("hello"),
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true,
            )
        }
    }
}

// 타입이 Daily이고 본인에 해당하는 타입이 없으며 record가 다른게 하나 존재하는 경우
@Preview
@Composable
private fun DayCellFilterTypeDailyOneRecordNoSchedulePreview() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = 2025,
                month = 9,
                day = 13,
                filterType = DAILY,
                isSelected = true,
                mainRecordType = DAILY,
                records = listOf(HABIT),
                schedules = listOf("hello"),
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true,
            )
        }
    }
}

// 타입이 스케줄이고 본인에 해당하는 타입이 없으며 record가 다른게 하나 존재하는 경우
@Preview
@Composable
private fun DayCellFilterTypeScheduleOneRecordNoSchedulePreview() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = 2025,
                month = 9,
                day = 13,
                filterType = SCHEDULE,
                isSelected = true,
                mainRecordType = DAILY,
                records = listOf(HABIT),
                schedules = listOf("hello"),
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                },
                isSameMonth = true,
            )
        }
    }
}
// 상황별 분리
