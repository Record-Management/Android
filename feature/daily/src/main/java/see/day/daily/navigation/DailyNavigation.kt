package see.day.daily.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import see.day.daily.screen.DailyScreenRoot
import see.day.model.record.RecordType

@Serializable data object Daily


fun NavController.navigateDaily(navOptions: NavOptions? = null) {
    navigate(Daily,navOptions)
}

fun NavGraphBuilder.dailyNavigation(onClickBackButton : () -> Unit, onClickChangeRecordType: (RecordType, Boolean) -> Unit) {
    composable<Daily> {
        DailyScreenRoot(onClickBackButton = onClickBackButton, onChangedRecordType = onClickChangeRecordType)
    }
}
