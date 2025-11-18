package see.day.goal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.goal.screen.CurrentGoalScreenRoot
import see.day.goal.screen.ResetGoalScreenRoot
import see.day.navigation.goal.GoalRoute
import see.day.navigation.goal.GoalRoute.CurrentGoal
import see.day.navigation.goal.GoalRoute.ResetGoal

fun NavController.navigateCurrentGoal(navOptions: NavOptions? = null) {
    navigate(CurrentGoal, navOptions)
}

fun NavController.navigateResetGoal(navOptions: NavOptions? = null) {
    navigate(ResetGoal, navOptions)
}

fun NavGraphBuilder.goalNavigation(
    onBack: () -> Unit,
    onClickResetGoal: () -> Unit
) {
    composable<CurrentGoal> { navBackStackEntry ->
        CurrentGoalScreenRoot(
            onBack = onBack,
            onClickResetGoal = onClickResetGoal
        )
    }
    composable<ResetGoal> {
        ResetGoalScreenRoot()
    }
}
