package see.day.daily.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.daily.R
import see.day.daily.component.EmotionAndDate
import see.day.daily.util.DailyDetailType
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.util.DailyEmotion
import see.day.model.record.RecordType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import see.day.ui.dialog.RecordDetailBackDialog
import see.day.ui.topbar.DetailRecordTopBar

@Composable
internal fun DailyDetailScreenRoot(modifier: Modifier = Modifier, dailyDetailType: DailyDetailType, onClickPopHome: () -> Unit) {
    var openBackDialog by remember { mutableStateOf(false) }
    val currentTime by remember { mutableStateOf(DateTime.now(DateTime.korea)) }
    val timeFormatter by remember { mutableStateOf(KoreanDateTimeFormatter(currentTime)) }
    if (openBackDialog) {
        RecordDetailBackDialog(
            modifier = modifier,
            onDismiss = { openBackDialog = false },
            onBackRecordDetail = onClickPopHome,
            title = R.string.record_close_dialog_title,
            body = R.string.record_close_dialog_body,
        )
    }
    BackHandler {
        openBackDialog = true
    }
    val emotion = when (dailyDetailType) {
        is DailyDetailType.WriteDailyDetail -> {
            dailyDetailType.emotion
        }

        is DailyDetailType.EditDailyDetail -> {
            DailyEmotion.LOVE
        }
    }
    DailyDetailScreen(
        modifier = modifier,
        dailyDetailType = dailyDetailType,
        onClickBackButton = { openBackDialog = true },
        emotion = emotion,
        currentDate = timeFormatter.formatDate(),
        currentTime = timeFormatter.formatTime()
    )
}

@Composable
internal fun DailyDetailScreen(
    modifier: Modifier = Modifier,
    dailyDetailType: DailyDetailType,
    onClickBackButton: () -> Unit,
    emotion: DailyEmotion,
    currentDate: String,
    currentTime: String
) {
    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            DetailRecordTopBar(
                modifier = modifier,
                recordType = RecordType.DAILY,
                onClickCloseButton = onClickBackButton
            )

        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(start = 16.dp)
        ) {
            EmotionAndDate(modifier, emotion, currentDate, currentTime,{})
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
}

@Preview
@Composable
private fun DailyDetailWriteScreen() {
    SeeDayTheme {
        DailyDetailScreen(
            dailyDetailType = DailyDetailType.WriteDailyDetail(DailyEmotion.FRUSTRATION),
            onClickBackButton = {},
            emotion = DailyEmotion.FRUSTRATION,
            currentDate = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)).formatDate(),
            currentTime = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)).formatTime()
        )
    }
}
