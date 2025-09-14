package see.day.onboarding.screen.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray40
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.ui.button.CompleteButton
import see.day.ui.picker.WheelDatePicker
import see.day.ui.picker.WheelPickerDefaults

@Composable
internal fun BirthdayScreen(modifier: Modifier = Modifier, birthDay: String, onClickComplete: (OnboardingUiEvent) -> Unit) {
    var year by remember { mutableStateOf(birthDay.split("-")[0].toInt()) }
    var month by remember { mutableStateOf(birthDay.split("-")[1].toInt()) }
    var day by remember { mutableStateOf(birthDay.split("-")[2].toInt()) }

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        WheelDatePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(210.dp),
            rowCount = 7,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                color = gray40,
                shape = RoundedCornerShape(0),
                border = BorderStroke(0.dp, gray40)
            ),
            startDate = LocalDate.of(year, month, day),
            maxDate = LocalDate.now(),
            textStyle = MaterialTheme.typography.titleMedium,
            textColor = gray100
        ) { snappedDate ->
            year = snappedDate.year
            month = snappedDate.monthValue
            day = snappedDate.dayOfMonth
        }
        Spacer(modifier = modifier.weight(1f))
        CompleteButton(
            modifier = modifier,
            text = "다음",
            isEnabled = true,
            onClick = { onClickComplete(OnboardingUiEvent.EnterBirthDay("%04d-%02d-%02d".format(year, month, day))) }
        )
    }
}

@Preview
@Composable
private fun BirthdayScreenPreview() {
    SeeDayTheme {
        BirthdayScreen(
            birthDay = "2025-09-09",
            onClickComplete = {}
        )
    }
}
