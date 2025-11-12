package see.day.goal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.goal.screen.CurrentGoalScreenRoot
import see.day.navigation.goal.GoalRoute

fun NavController.navigateCurrentGoal(navOptions: NavOptions? = null) {
    navigate(GoalRoute.CurrentGoal, navOptions)
}

fun NavGraphBuilder.goalNavigation(
    onBack: () -> Unit
) {
    composable<GoalRoute.CurrentGoal> { navBackStackEntry ->
        CurrentGoalScreenRoot(
            onBack = onBack
        )
    }
}
