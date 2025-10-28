package see.day.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.navigation.setting.SettingRoute
import see.day.navigation.setting.SettingRoute.Setting
import see.day.navigation.setting.SettingRoute.SettingGoalNotification
import see.day.setting.screen.SettingGoalNotificationScreenRoot
import see.day.setting.screen.SettingScreenRoot

fun NavController.navigateSetting(navOptions: NavOptions? = null) {
    navigate(Setting, navOptions)
}

fun NavController.navigateSettingGoalNotification(navOptions: NavOptions? = null) {
    navigate(SettingGoalNotification, navOptions)
}

fun NavGraphBuilder.settingNavigation(
    onBack: () -> Unit,
    onGoSettingGoalNotification: () -> Unit
) {
    composable<Setting> {
        SettingScreenRoot(
            onBack = onBack,
            onGoSettingGoalNotification = onGoSettingGoalNotification
        )
    }

    composable<SettingGoalNotification> {
        SettingGoalNotificationScreenRoot(
            onBack = onBack
        )
    }
}
