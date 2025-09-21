package see.day.daily.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import see.day.daily.R
import see.day.daily.util.DailyDetailType

@Composable
internal fun DailyDetailScreenRoot(modifier: Modifier = Modifier, dailyDetailType: DailyDetailType, onClickPopHome: () -> Unit) {
    BackHandler {
        onClickPopHome()
    }
    DailyDetailScreen(
        modifier = modifier,
        dailyDetailType = dailyDetailType,
        onClickBackButton = onClickPopHome
    )
}

@Composable
internal fun DailyDetailScreen(modifier: Modifier = Modifier, dailyDetailType: DailyDetailType, onClickBackButton: () -> Unit) {
    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            Row {
                Spacer(modifier = modifier.weight(1f))
                Image(
                    modifier = modifier
                        .padding(top = 16.dp, end = 16.dp)
                        .clickable { onClickBackButton() },
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "뒤로가기 버튼"
                )
            }
        }
    ) {

    }
    Column {
        when (dailyDetailType) {
            is DailyDetailType.WriteDailyDetail -> {
                Text("쓰기 ${dailyDetailType.emotion}")
            }

            is DailyDetailType.EditDailyDetail -> {
                Text("수정하기 ${dailyDetailType.id}")
            }
        }
    }
}
