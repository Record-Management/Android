package see.day.exercise.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import see.day.exercise.screen.ExerciseDetailScreenRoot
import see.day.exercise.screen.ExerciseSelectScreenRoot
import see.day.exercise.util.ExerciseRecordPostType
import see.day.model.record.RecordType
import see.day.model.record.exercise.ExerciseType
import see.day.navigation.exercise.ExerciseRoute.*

fun NavController.navigateExercise(navOptions: NavOptions? = null) {
    navigate(ExerciseSelect, navOptions)
}

fun NavController.navigateExerciseWrite(exerciseType: ExerciseType, navOptions: NavOptions? = null) {
    navigate(ExerciseWrite(exerciseType), navOptions)
}

fun NavController.navigateExerciseDetail(recordId: String, navOptions: NavOptions? = null) {
    navigate(ExerciseDetail(recordId), navOptions)
}

fun NavGraphBuilder.exerciseNavigation(onClickChangeRecordType: (RecordType, Boolean) -> Unit, onBack: () -> Unit, onClickExerciseType: (ExerciseType) -> Unit, onClickPopHome: (Boolean) -> Unit) {
    composable<ExerciseSelect> {
        ExerciseSelectScreenRoot(onClickChangeRecordType = onClickChangeRecordType, onBack = onBack, onClickExerciseType = onClickExerciseType)
    }

    composable<ExerciseWrite> { navBackStackEntry ->
        val exerciseType = navBackStackEntry.toRoute<ExerciseWrite>().exerciseType
        ExerciseDetailScreenRoot(
            editType = ExerciseRecordPostType.Write(exerciseType),
            onClickPopHome = onClickPopHome
        )
    }

    composable<ExerciseDetail> { navBackStackEntry ->
        val recordId = navBackStackEntry.toRoute<ExerciseDetail>().recordId
        ExerciseDetailScreenRoot(
          editType = ExerciseRecordPostType.Edit(recordId),
            onClickPopHome = onClickPopHome
        )

    }
}
