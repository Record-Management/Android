package see.day.daily.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import see.day.daily.util.DailyDetailType

@Composable
internal fun DailyDetailScreenRoot(
    modifier: Modifier = Modifier,
    dailyDetailType: DailyDetailType
) {
    DailyDetailScreen(
        modifier = modifier,
        dailyDetailType = dailyDetailType
    )
}

@Composable
internal fun DailyDetailScreen(
    modifier: Modifier = Modifier,
    dailyDetailType: DailyDetailType
) {
    Column {
        when(dailyDetailType) {
            is DailyDetailType.WriteDailyDetail -> {
                Text("쓰기 ${dailyDetailType.emotion}")
            }
            is DailyDetailType.EditDailyDetail -> {
                Text("수정하기 ${dailyDetailType.id}")
            }
        }
    }
}
