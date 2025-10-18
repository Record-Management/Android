package see.day.main.navigation.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.model.record.RecordType
import see.day.navigation.schedule.ScheduleRoute.Schedule
import see.day.ui.dialog.RecordTypePickerDialog

fun NavController.navigateSchedule(navOptions: NavOptions? = null) {
    navigate(Schedule, navOptions)
}

fun NavGraphBuilder.scheduleNavigation(onClickChangeRecordType: (RecordType, Boolean) -> Unit) {
    composable<Schedule> {
        Column {
            Text("Schedule")
            var isOpenRecordTypePickerDialog by remember { mutableStateOf(false) }

            if (isOpenRecordTypePickerDialog) {
                RecordTypePickerDialog(
                    currentRecordType = RecordType.SCHEDULE,
                    onDismiss = { isOpenRecordTypePickerDialog = false },
                    onCompleteRecordType = { selectedType ->
                        onClickChangeRecordType(selectedType, true)
                        isOpenRecordTypePickerDialog = false
                    }
                )
            }

            Button(
                { isOpenRecordTypePickerDialog = true }
            ) {
                Text("기록 변경")
            }
        }
    }
}
