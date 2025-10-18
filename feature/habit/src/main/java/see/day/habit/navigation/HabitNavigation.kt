package see.day.habit.navigation

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
import see.day.navigation.habit.HabitRoute.HabitSelect
import see.day.ui.dialog.RecordTypePickerDialog

fun NavController.navigateHabit(navOptions: NavOptions? = null) {
    navigate(HabitSelect, navOptions)
}

fun NavGraphBuilder.habitNavigation(onClickChangeRecordType: (RecordType, Boolean) -> Unit) {
    composable<HabitSelect> {
        Column {
            Text("Habit")
            var isOpenRecordTypePickerDialog by remember { mutableStateOf(false) }

            if (isOpenRecordTypePickerDialog) {
                RecordTypePickerDialog(
                    currentRecordType = RecordType.HABIT,
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
