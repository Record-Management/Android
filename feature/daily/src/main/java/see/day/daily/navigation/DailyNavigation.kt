package see.day.daily.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import see.day.daily.screen.DailyDetailScreen
import see.day.daily.screen.DailyScreenRoot
import see.day.daily.util.DailyDetailType
import see.day.designsystem.util.DailyEmotion
import see.day.model.record.RecordType

@Serializable data object Daily

@Serializable data class DailyWrite(val emotion: DailyEmotion)

@Serializable data class DailyDetail(val id: String)


fun NavController.navigateDaily(navOptions: NavOptions? = null) {
    navigate(Daily, navOptions)
}

fun NavController.navigateDailyWrite(emotion: DailyEmotion, navOptions: NavOptions? = null) {
    navigate(DailyWrite(emotion), navOptions)
}

fun NavGraphBuilder.dailyNavigation(onClickBackButton: () -> Unit, onClickChangeRecordType: (RecordType, Boolean) -> Unit, onClickEmotion: (DailyEmotion) -> Unit) {
    composable<Daily> {
        DailyScreenRoot(onClickBackButton = onClickBackButton, onChangedRecordType = onClickChangeRecordType, onClickEmotion = onClickEmotion)
    }
    composable<DailyWrite> { backStackEntry ->
        val dailyWrite = backStackEntry.toRoute<DailyWrite>()
        DailyDetailScreen(
            dailyDetailType = DailyDetailType.WriteDailyDetail(dailyWrite.emotion)
        )
    }
}
