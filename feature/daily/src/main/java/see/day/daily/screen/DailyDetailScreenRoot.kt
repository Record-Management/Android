package see.day.daily.screen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.daily.R
import see.day.daily.component.EmotionAndDate
import see.day.daily.component.EmotionSelectBottomSheet
import see.day.daily.util.DailyDetailType
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray60
import see.day.designsystem.util.DailyEmotion
import see.day.model.record.RecordType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import see.day.ui.button.CompleteButtonNoHorizontalPadding
import see.day.ui.dialog.RecordDetailBackDialog
import see.day.ui.photo.RecordDetailPhotoRow
import see.day.ui.textField.RecordWriteTextField
import see.day.ui.topbar.DetailRecordTopBar

@OptIn(ExperimentalMaterial3Api::class)
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

    var openSelectEmotionDialog by remember { mutableStateOf(false) }
    var emotion by remember {
        mutableStateOf(
            when (dailyDetailType) {
                is DailyDetailType.WriteDailyDetail -> {
                    dailyDetailType.emotion
                }

                is DailyDetailType.EditDailyDetail -> {
                    DailyEmotion.LOVE
                }
            }
        )
    }
    val onClickChangeEmotion: (DailyEmotion) -> Unit = { selectedEmotion ->
        emotion = selectedEmotion
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (openSelectEmotionDialog) {
        EmotionSelectBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismiss = { openSelectEmotionDialog = false },
            onClickChangeEmotion = onClickChangeEmotion
        )
    }
    val (text, onChangedText) = rememberSaveable { mutableStateOf("") }
    var uris by remember { mutableStateOf(listOf<Uri>()) }
    val onClickAddPhotos: (List<Uri>) -> Unit = { selectedUris ->
        val addedUris = (uris + selectedUris).distinct()
        uris = if (addedUris.size <= 3) {
            addedUris
        } else {
            addedUris.subList(0, 3)
        }
    }
    DailyDetailScreen(
        modifier = modifier,
        dailyDetailType = dailyDetailType,
        emotion = emotion,
        text = text,
        uris = uris,
        currentDate = timeFormatter.formatDate(),
        currentTime = timeFormatter.formatTime(),
        onClickBackButton = { openBackDialog = true },
        onClickEmotion = { openSelectEmotionDialog = true },
        onTextChanged = onChangedText,
        onClickAddPhotos = onClickAddPhotos,
        onRemovePhotos = { removedPhoto ->
            uris = uris.filter {
                removedPhoto != it
            }
        }
    )
}

@Composable
internal fun DailyDetailScreen(
    modifier: Modifier = Modifier,
    dailyDetailType: DailyDetailType,
    emotion: DailyEmotion,
    text: String,
    currentDate: String,
    currentTime: String,
    uris: List<Uri>,
    onClickBackButton: () -> Unit,
    onClickEmotion: () -> Unit,
    onTextChanged: (String) -> Unit,
    onClickAddPhotos: (List<Uri>) -> Unit,
    onRemovePhotos: (Uri) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier
            .systemBarsPadding()
            .background(Color.White),
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
                .padding(horizontal = 16.dp)
        ) {
            EmotionAndDate(modifier, emotion, currentDate, currentTime, onClickEmotion)
            Spacer(modifier = modifier.padding(top = 24.dp))
            RecordWriteTextField(
                modifier = modifier,
                text = text,
                placeHolder = R.string.record_daily_place_holder,
                onChangedText = onTextChanged
            )
            RecordDetailPhotoRow(modifier, context, uris, onRemovePhotos, onClickAddPhotos)
            Text(
                modifier = modifier.padding(top = 10.dp),
                text = stringResource(R.string.photo_limit_text),
                color = gray60,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = modifier.weight(1f))
            CompleteButtonNoHorizontalPadding(
                text = stringResource(see.day.ui.R.string.write_record_text),
                isEnabled = text.isNotEmpty(),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun DailyDetailWriteScreen() {
    val (text, onTextChanged) = rememberSaveable { mutableStateOf("") }
    SeeDayTheme {
        DailyDetailScreen(
            dailyDetailType = DailyDetailType.WriteDailyDetail(DailyEmotion.FRUSTRATION),
            emotion = DailyEmotion.FRUSTRATION,
            text = text,
            currentDate = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)).formatDate(),
            currentTime = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)).formatTime(),
            uris = listOf(),
            onClickBackButton = { },
            onClickEmotion = { },
            onTextChanged = onTextChanged,
            onClickAddPhotos = {},
            onRemovePhotos = {}
        )
    }
}
