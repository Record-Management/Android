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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

@Composable
fun DayCell(
    modifier: Modifier = Modifier,
    isSameMonth: Boolean = true,
    isSelected: Boolean = false,
    year: Int,
    month: Int,
    day: Int,
    mainRecordType: RecordType,
    records: List<RecordType>,
    schedules : List<String>,
    onClickItem: (Int, Int, Int) -> Unit
) {
    Column(
        modifier = modifier
            .heightIn(min = 80.dp)
            .fillMaxWidth()
            .clickable { onClickItem(year ,month, day) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.toString(),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = if(isSelected) Color.White else if(isSameMonth) gray100 else gray40,
            modifier = if(isSelected) {
                modifier.fillMaxWidth().padding(horizontal = 17.dp).clip(RoundedCornerShape(100.dp)).background(MaterialTheme.colorScheme.primary)
            } else {
                modifier.fillMaxWidth()
            }
        )
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
                    painter = painterResource(R.drawable.image_daily),
                    modifier = modifier.size(24.dp),
                    contentDescription = "이미지"
                )
            }
            // 점이 찍히는 조건
            Image(
                painter = painterResource(R.drawable.ic_red_dot),
                modifier = modifier
                    .size(6.dp)
                    .align(Alignment.TopCenter)
                    .offset(x = (14.5).dp),
                contentDescription = "선택된 버튼",
            )
        }
        if(schedules.isNotEmpty()) {
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
                Spacer(modifier = modifier
                    .padding(2.dp)
                    .size(width = 2.dp, height = 10.dp)
                    .background(gray50))
                Text(
                    text = schedules[0],
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.padding(bottom = 1.dp)
                )
            }
            if(schedules.size >= 2) {
                Text(
                    text = "+${schedules.size-1}",
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

@Preview
@Composable
private fun DayCellPreview() {
    val context = LocalContext.current
    SeeDayTheme {
        Column(
            modifier = Modifier.width(49.dp)
        ) {
            DayCell(
                year = 2025,
                month = 9,
                day = 13,
                mainRecordType = RecordType.DAILY,
                records = listOf(RecordType.DAILY),
                schedules = listOf("일정이있습니다.","하나 더 있습니다."),
                onClickItem = { year, month, day ->
                    Toast.makeText(context, "$year $month $day", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Preview
@Composable
private fun DayCellsPreview() {
    val context = LocalContext.current
    val list = (1..35).toList()
    SeeDayTheme{
        LazyVerticalGrid (
            columns = GridCells.Fixed(7)
        ) {
            items(items = list, key = { it}) { it ->
                DayCell(
                    year = 2025,
                    month = 1,
                    day = it,
                    mainRecordType = RecordType.DAILY,
                    records = listOf(RecordType.DAILY),
                    schedules = listOf("일정이있습니다.","하나 더 있습니다."),
                    onClickItem = { year, month, day ->
                        Toast.makeText(context, "$month $day", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
