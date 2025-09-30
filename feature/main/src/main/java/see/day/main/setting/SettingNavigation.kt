package see.day.main.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import see.day.navigation.setting.Setting

fun NavController.navigateSetting(navOptions: NavOptions? = null) {
    navigate(Setting, navOptions)
}
fun NavGraphBuilder.settingNavigation() {
    composable<Setting> {
        SettingScreenRoot()
    }
}
