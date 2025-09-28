package see.day.daily.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import see.day.daily.screen.DailyDetailScreenRoot
import see.day.daily.screen.DailyScreenRoot
import see.day.daily.util.DailyRecordPostType
import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion
import see.day.navigation.daily.DailyRoute.Daily
import see.day.navigation.daily.DailyRoute.DailyWrite


fun NavController.navigateDaily(navOptions: NavOptions? = null) {
    navigate(Daily, navOptions)
}

fun NavController.navigateDailyWrite(emotion: DailyEmotion, navOptions: NavOptions? = null) {
    navigate(DailyWrite(emotion), navOptions)
}

fun NavGraphBuilder.dailyNavigation(
    onClickBackButton: () -> Unit,
    onClickChangeRecordType: (RecordType, Boolean) -> Unit,
    onClickEmotion: (DailyEmotion) -> Unit,
    onClickPopHome: () -> Unit
) {
    composable<Daily> {
        DailyScreenRoot(onClickBackButton = onClickBackButton, onChangedRecordType = onClickChangeRecordType, onClickEmotion = onClickEmotion)
    }
    composable<DailyWrite> { backStackEntry ->
        val dailyWrite = backStackEntry.toRoute<DailyWrite>()
        DailyDetailScreenRoot(
            dailyRecordPostType = DailyRecordPostType.WriteDailyRecordPost(dailyWrite.emotion),
            onClickPopHome = onClickPopHome
        )
    }
}
