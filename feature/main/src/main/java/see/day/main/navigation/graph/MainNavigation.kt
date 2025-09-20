package see.day.main.navigation.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import see.day.main.Greeting
import see.day.model.record.RecordType
import see.day.ui.dialog.RecordTypePickerDialog

const val MAIN_ROUTE = "MAIN"

fun NavGraphBuilder.mainNavigation(onClickLogin: () -> Unit) {
    composable(MAIN_ROUTE) {
        Greeting(
            "hi",
            modifier = Modifier,
            onClickLogin = onClickLogin
        )
    }
}

@Serializable data object Exercise

@Serializable data object Habit

@Serializable data object Schedule

fun NavController.navigateExercise(navOptions: NavOptions? = null) {
    navigate(Exercise, navOptions)
}

fun NavGraphBuilder.exerciseNavigation(onClickChangeRecordType: (RecordType, Boolean) -> Unit) {
    composable<Exercise> {
        Column {
            Text("Exercise")
            var isOpenRecordTypePickerDialog by remember { mutableStateOf(false) }

            if (isOpenRecordTypePickerDialog) {
                RecordTypePickerDialog(
                    currentRecordType = RecordType.EXERCISE,
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

fun NavController.navigateHabit(navOptions: NavOptions? = null) {
    navigate(Habit, navOptions)
}

fun NavGraphBuilder.habitNavigation(onClickChangeRecordType: (RecordType, Boolean) -> Unit) {
    composable<Habit> {
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
