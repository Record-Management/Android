package see.day.setting.component

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray40
import see.day.designsystem.theme.gray50
import see.day.setting.R
import see.day.ui.dialog.DialogBackground
import see.day.ui.picker.WheelDatePicker
import see.day.ui.picker.WheelPickerDefaults
import java.time.LocalDate

@Composable
internal fun BirthDateChangedDialog(
    modifier: Modifier = Modifier,
    birthDate: String,
    onDismiss: () -> Unit,
    onBirthDateChanged: (String) -> Unit
) {
    var (year, month, day) = remember(birthDate) { birthDate.split("-").map { it.toInt() } }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        DialogBackground(
            modifier = modifier,
            onDismiss = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 33.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .clickable(onClick = {}, indication = null, interactionSource = remember { MutableInteractionSource() })
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.change_birthdate),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                WheelDatePicker(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    rowCount = 5,
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        color = gray40,
                        shape = RoundedCornerShape(0),
                        border = BorderStroke(0.dp, gray40)
                    ),
                    startDate = startDate(year, month, day),
                    maxDate = LocalDate.now().minusYears(3),
                    textStyle = MaterialTheme.typography.titleMedium,
                    textColor = gray100
                ) { snappedDate ->
                    year = snappedDate.year
                    month = snappedDate.monthValue
                    day = snappedDate.dayOfMonth
                }
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 52.dp)
                            .padding(end = 5.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = gray20
                        )
                    ) {
                        Text(
                            stringResource(R.string.cancel),
                            color = gray50,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                    Button(
                        onClick = {
                            onBirthDateChanged(String.format("%04d-%02d-%02d", year, month, day))
                        },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 52.dp)
                            .padding(start = 5.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            stringResource(R.string.revise),
                            color = Color.White,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
            }
        }
    }
}

private fun startDate(year: Int, month: Int, day: Int): LocalDate  {
    return if (LocalDate.of(year, month, day) > LocalDate.now().minusYears(3)) LocalDate.now().minusYears(3)
    else LocalDate.of(year, month, day)
}

@Preview
@Composable
private fun BirthDateChangedDialogPreview() {
    val context = LocalContext.current
    SeeDayTheme {
        BirthDateChangedDialog(
            birthDate = "2000-01-16",
            onDismiss = {},
            onBirthDateChanged = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show()}
        )
    }
}
