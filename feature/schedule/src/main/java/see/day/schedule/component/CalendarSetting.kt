package see.day.schedule.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray40
import see.day.designsystem.theme.gray50
import see.day.schedule.R
import see.day.schedule.screen.toDateString
import see.day.ui.picker.WheelDatePicker
import see.day.ui.picker.WheelPickerDefaults
import java.time.LocalDate

@Composable
internal fun CalendarSetting(
    modifier: Modifier = Modifier,
    startDate: LocalDate,
    endDate: LocalDate,
    setStartDate: (LocalDate) -> Unit,
    setEndDate: (LocalDate) -> Unit
) {
    var isShowStartDatePicker by remember { mutableStateOf(false) }
    var isShowEndDatePicker by remember { mutableStateOf(false) }
    val isAnyDatePickerOpen = isShowStartDatePicker || isShowEndDatePicker

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = "달력 아이콘",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = stringResource(R.string.calendar),
            style = MaterialTheme.typography.titleSmall
        )
    }
    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ScheduleDateText(
            date = startDate,
            isClicked = isShowStartDatePicker,
            isAnyDatePickerOpen = isAnyDatePickerOpen,
            onClickDate = {
                if (!isShowStartDatePicker) {
                    isShowStartDatePicker = true
                }
                if (isShowEndDatePicker) {
                    isShowEndDatePicker = false
                }
            }
        )
        Icon(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "체크 아이콘",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        ScheduleDateText(
            date = endDate,
            isClicked = isShowEndDatePicker,
            isAnyDatePickerOpen = isAnyDatePickerOpen,
            onClickDate = {
                if (!isShowEndDatePicker) {
                    isShowEndDatePicker = true
                }
                if (isShowStartDatePicker) {
                    isShowStartDatePicker = false
                }
            }
        )
    }

    if (isShowStartDatePicker || isShowEndDatePicker) {
        val selectedDatePickerKey = if (isShowStartDatePicker) "start" else "end"

        key(selectedDatePickerKey) {
            WheelDatePicker(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(170.dp),
                rowCount = 5,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    color = gray40,
                    shape = RoundedCornerShape(0),
                    border = BorderStroke(0.dp, gray40)
                ),
                startDate = if (isShowStartDatePicker) startDate else endDate,
                minDate = if (isShowStartDatePicker) LocalDate.MIN else startDate,
                maxDate = LocalDate.MAX,
                textStyle = MaterialTheme.typography.titleMedium,
                textColor = gray100
            ) { snappedDate ->
                if (isShowStartDatePicker) {
                    setStartDate(snappedDate)
                    if (snappedDate > endDate) {
                        setEndDate(snappedDate)
                    }
                }
                if (isShowEndDatePicker) {
                    setEndDate(snappedDate)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {
                        isShowStartDatePicker = false
                        isShowEndDatePicker = false
                    },
                text = "완료",
                style = MaterialTheme.typography.titleSmall,
                color = gray100
            )
        }
    }
}

@Composable
private fun ScheduleDateText(
    date: LocalDate,
    isClicked: Boolean,
    isAnyDatePickerOpen: Boolean,
    onClickDate: () -> Unit,
) {

    Box(
        modifier = Modifier
            .size(height = 40.dp, width = 148.dp)
            .clickable { onClickDate() }
            .background(gray20, RoundedCornerShape(8.dp))
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = date.toDateString(),
            style = MaterialTheme.typography.titleSmall.copy(
                color = if (!isAnyDatePickerOpen || isClicked) gray100 else gray50
            ),
            textAlign = TextAlign.Center
        )
    }
}
