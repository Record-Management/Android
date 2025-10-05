package see.day.exercise.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.exercise.screen.ExerciseSelectScreenRoot
import see.day.model.record.RecordType
import see.day.navigation.exercise.ExerciseRoute.ExerciseSelect

fun NavController.navigateExercise(navOptions: NavOptions? = null) {
    navigate(ExerciseSelect, navOptions)
}

fun NavGraphBuilder.exerciseNavigation(onClickChangeRecordType: (RecordType, Boolean) -> Unit, onBack: () -> Unit) {
    composable<ExerciseSelect> {
        ExerciseSelectScreenRoot(onClickChangeRecordType = onClickChangeRecordType, onBack = onBack)
    }
}
