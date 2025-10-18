package see.day.habit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.habit.screen.HabitSelectScreenRoot
import see.day.model.record.RecordType
import see.day.navigation.habit.HabitRoute.HabitSelect

fun NavController.navigateHabit(navOptions: NavOptions? = null) {
    navigate(HabitSelect, navOptions)
}

fun NavGraphBuilder.habitNavigation(onClickChangeRecordType: (RecordType, Boolean) -> Unit, onBack: () -> Unit) {
    composable<HabitSelect> {
        HabitSelectScreenRoot(
            onClickChangedRecordType = onClickChangeRecordType,
            onBack = onBack
        )
    }
}
