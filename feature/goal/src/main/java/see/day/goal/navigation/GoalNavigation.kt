package see.day.goal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import see.day.goal.screen.CurrentGoalScreen
import see.day.goal.screen.CurrentGoalScreenRoot
import see.day.navigation.goal.GoalRoute

fun NavController.navigateCurrentGoal(userId: String, navOptions: NavOptions? = null) {
    navigate(GoalRoute.CurrentGoal(userId), navOptions)
}

fun NavGraphBuilder.goalNavigation(
    onBack: () -> Unit
) {
    composable<GoalRoute.CurrentGoal> { navBackStackEntry ->
        val userId = navBackStackEntry.toRoute<GoalRoute.CurrentGoal>().userId
        CurrentGoalScreenRoot(
            onBack = onBack,
            userId = userId
        )
    }
}
