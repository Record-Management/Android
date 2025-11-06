package see.day.habit.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.habit.R
import see.day.ui.dialog.ConfirmDialog

@Composable
internal fun ChangeMainHabitRecordComponent(
    modifier: Modifier = Modifier,
    isMainHabit: Boolean,
    onChangedMainHabit: (Boolean) -> Unit
) {
    var openMainHabitChangeDialog by remember { mutableStateOf(false) }

    if(openMainHabitChangeDialog) {
        ConfirmDialog(
            title =R.string.main_habit_change_title,
            body = R.string.main_habit_change_body,
            cancel = R.string.changed_main_cancel,
            confirm = R.string.changed_main_change,
            onDismiss = {openMainHabitChangeDialog = false},
            onClickConfirmButton = {
                onChangedMainHabit(true)
            }
        )
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_pin),
            contentDescription = "메인 기록 설정 핀",
            modifier = Modifier.size(24.dp)
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = stringResource(R.string.change_main),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isMainHabit,
            onCheckedChange = { newChecked ->
                if(newChecked) {
                    openMainHabitChangeDialog = true
                } else {
                    onChangedMainHabit(false)
                }
            },
            colors = SwitchDefaults.colors().copy(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF2DC86F),
                checkedBorderColor = Color.White,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFD7D7D7),
                uncheckedBorderColor = Color.White,
            ),
        )
    }
}

@Preview
@Composable
private fun ChangeMainHabitRecordComponentPreview() {
    val (isMainHabit, onChangedMainHabit) = remember { mutableStateOf(false) }
    SeeDayTheme {
        ChangeMainHabitRecordComponent(
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
            isMainHabit = isMainHabit,
            onChangedMainHabit = onChangedMainHabit
        )
    }
}
