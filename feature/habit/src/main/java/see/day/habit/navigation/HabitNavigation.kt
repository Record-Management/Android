package see.day.habit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import see.day.habit.screen.HabitDetailScreenRoot
import see.day.habit.screen.HabitSelectScreenRoot
import see.day.habit.state.HabitRecordPostType
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitType
import see.day.navigation.habit.HabitRoute.HabitSelect
import see.day.navigation.habit.HabitRoute.HabitWrite

fun NavController.navigateHabit(navOptions: NavOptions? = null) {
    navigate(HabitSelect, navOptions)
}

fun NavController.navigateHabitWrite(habitType: HabitType, navOptions: NavOptions? = null) {
    navigate(HabitWrite(habitType), navOptions)
}

fun NavGraphBuilder.habitNavigation(onClickChangeRecordType: (RecordType, Boolean) -> Unit, onBack: () -> Unit, onClickHabitType: (HabitType) -> Unit) {
    composable<HabitSelect> {
        HabitSelectScreenRoot(
            onClickChangedRecordType = onClickChangeRecordType,
            onBack = onBack,
            onClickHabitType = onClickHabitType
        )
    }

    composable<HabitWrite> { navBackStackEntry ->
        val habitType = navBackStackEntry.toRoute<HabitWrite>().habitType
        HabitDetailScreenRoot(editType = HabitRecordPostType.Write(habitType))
    }
}
