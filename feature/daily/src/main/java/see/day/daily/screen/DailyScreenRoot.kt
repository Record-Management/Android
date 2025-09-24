package see.day.daily.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.daily.R
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.util.DailyEmotion
import see.day.designsystem.util.largeIconRes
import see.day.model.record.RecordType
import see.day.ui.dialog.RecordTypePickerDialog

@Composable
internal fun DailyScreenRoot(modifier: Modifier = Modifier, onClickBackButton: () -> Unit, onChangedRecordType: (RecordType, Boolean) -> Unit, onClickEmotion: (DailyEmotion) -> Unit) {
    DailyScreen(modifier, onClickBackButton, onChangedRecordType, onClickEmotion)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DailyScreen(modifier: Modifier = Modifier, onClickBackButton: () -> Unit, onChangedRecordType: (RecordType, Boolean) -> Unit, onClickEmotion: (DailyEmotion) -> Unit) {
    var openChangeRecordDialog by remember { mutableStateOf(false) }
    if (openChangeRecordDialog) {
        RecordTypePickerDialog(
            currentRecordType = RecordType.DAILY,
            onDismiss = { openChangeRecordDialog = false },
            onCompleteRecordType = { changedType ->
                onChangedRecordType(changedType, true)
                openChangeRecordDialog = false
            }
        )
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = modifier.weight(1f))
                        Image(
                            modifier = modifier
                                .padding(top = 16.dp, end = 16.dp)
                                .clickable { onClickBackButton() },
                            painter = painterResource(see.day.ui.R.drawable.ic_close),
                            contentDescription = "뒤로가기 버튼"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.padding(top = 60.dp),
                text = stringResource(R.string.select_daily_emotion),
                style = MaterialTheme.typography.bodySmall
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = modifier
                    .padding(top = 40.dp)
                    .padding(horizontal = 43.dp)
                    .fillMaxWidth()
            ) {
                items(items = DailyEmotion.entries.toList(), key = { it }) { emotion ->
                    Image(
                        painter = painterResource(emotion.largeIconRes),
                        contentDescription = "emotion ${emotion.name}",
                        modifier = modifier
                            .size(80.dp)
                            .clickable { onClickEmotion(emotion) },
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            Spacer(modifier = modifier.weight(1f))
            Text(
                text = stringResource(R.string.change_record_type),
                style = MaterialTheme.typography.labelMedium.copy(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = modifier
                    .padding(bottom = 52.dp)
                    .systemBarsPadding()
                    .clickable {
                        openChangeRecordDialog = true
                    }
            )
        }
    }
}

@Preview
@Composable
private fun DailyScreenPreview() {
    SeeDayTheme {
        DailyScreen(
            onClickBackButton = {},
            onChangedRecordType = { type, backstack -> },
            onClickEmotion = {}
        )
    }
}
