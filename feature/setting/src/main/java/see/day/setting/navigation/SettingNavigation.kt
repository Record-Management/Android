package see.day.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.navigation.setting.SettingRoute.Setting
import see.day.setting.screen.SettingScreenRoot

fun NavController.navigateSetting(navOptions: NavOptions? = null) {
    navigate(Setting, navOptions)
}
fun NavGraphBuilder.settingNavigation(onBack: () -> Unit) {
    composable<Setting> {
        SettingScreenRoot(
            onBack = onBack
        )
    }
}
