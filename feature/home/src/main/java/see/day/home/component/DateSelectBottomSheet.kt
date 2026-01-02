package see.day.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray40
import see.day.ui.button.GrayButton
import see.day.ui.picker.WheelDatePicker
import see.day.ui.picker.WheelPickerDefaults
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateSelectBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    year: Int,
    month: Int,
    dayOfMonth: Int,
    onDismiss: () -> Unit,
    onClickTodayDate: () -> Unit,
    onClickDate: (Int, Int, Int) -> Unit
) {
    var spinnerYear by remember { mutableIntStateOf(year) }
    var spinnerMonth by remember { mutableIntStateOf(month) }
    var spinnerDayOfMonth by remember { mutableIntStateOf(dayOfMonth) }
    Column(
        modifier = modifier.padding(24.dp)
    ) {
        ModalBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismissRequest = onDismiss,
            dragHandle = {},
            containerColor = Color.White
        ) {
            WheelDatePicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                rowCount = 5,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    color = gray40,
                    shape = RoundedCornerShape(0),
                    border = BorderStroke(0.dp, gray40)
                ),
                startDate = LocalDate.of(spinnerYear, spinnerMonth, spinnerDayOfMonth),
                maxDate = LocalDate.now(),
                textStyle = MaterialTheme.typography.titleMedium,
                textColor = gray100
            ) { snappedDate ->
                spinnerYear = snappedDate.year
                spinnerMonth = snappedDate.monthValue
                spinnerDayOfMonth = snappedDate.dayOfMonth
            }
            Row(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),

                ) {
                GrayButton(
                    modifier = Modifier.weight(1f),
                    text = "오늘",
                    isEnabled = true,
                    onClick = {
                        onClickTodayDate()
                        onDismiss()
                    }
                )
                GrayButton(
                    modifier = Modifier.weight(1f),
                    text = "완료",
                    isEnabled = true,
                    onClick = {
                        onClickDate(spinnerYear, spinnerMonth, spinnerDayOfMonth)
                        onDismiss()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DateSelectBottomSheetPreview() {
    SeeDayTheme {
        DateSelectBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            year = 2026,
            month = 1,
            dayOfMonth = 1,
            onDismiss = {},
            onClickTodayDate = {},
            onClickDate = { year, month, dayOfMonth ->

            }
        )
    }
}
