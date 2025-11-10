package see.day.ui.component.record.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray10
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray80
import see.day.designsystem.util.largeIconRes
import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DailyRecordOverview(modifier: Modifier = Modifier, recordId: String, dailyEmotion: DailyEmotion, recordDate: String, content: String, photoUrls: List<String>, onClickItem: (RecordType, String) -> Unit, onClickLongItem: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = { onClickItem(RecordType.DAILY, recordId) },
                onLongClick = { onClickLongItem() }
            )
            .background(gray10)
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(dailyEmotion.largeIconRes),
                contentDescription = "$dailyEmotion",
                modifier = modifier.size(64.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = modifier.weight(1f))
            Text(
                text = recordDate,
                style = MaterialTheme.typography.headlineMedium,
                color = gray80
            )
        }
        Text(
            modifier = modifier.padding(top = 10.dp),
            text = content,
            style = MaterialTheme.typography.labelSmall,
            color = gray100,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        if (photoUrls.isNotEmpty()) {
            LazyRow(
                modifier = modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(items = photoUrls) { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "photo $url",
                        modifier = modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DailyRecordOverviewHasPhotoPreview() {
    SeeDayTheme {
        DailyRecordOverview(
            recordId = "",
            dailyEmotion = DailyEmotion.Normal,
            recordDate = "오전 12:18",
            content = "오늘 아침에 일어나서 밥먹고 소화시키다 누워서 다시 자고 일어나서 다시 밥먹고 다시 자고나니 하루가 다 갔다.",
            photoUrls = listOf("https://wikidocs.net/images/page/49159/png-2702691_1920_back.png", "https://wikidocs.net/images/page/49159/png-2702691_1920_back.png", "https://wikidocs.net/images/page/49159/png-2702691_1920_back.png"),
            onClickItem = { recordType, recordId -> },
            onClickLongItem = {}
        )
    }
}

@Preview
@Composable
private fun DailyRecordOverviewOversizePreview() {
    SeeDayTheme {
        DailyRecordOverview(
            recordId = "",
            dailyEmotion = DailyEmotion.Normal,
            recordDate = "오전 12:18",
            content = "오늘 아침에 일어나서 밥먹고 소화시키다 누워서 다시 자고 일어나서 다시 밥먹고 다시 자고나니 하루가 다 갔다. 정말 즐거운 하루였고 내일도 즐거운 하루였으면 좋겠다 너무너무 행복하다",
            photoUrls = listOf("https://wikidocs.net/images/page/49159/png-2702691_1920_back.png", "https://wikidocs.net/images/page/49159/png-2702691_1920_back.png", "https://wikidocs.net/images/page/49159/png-2702691_1920_back.png"),
            onClickItem = { recordType, recordId -> },
            onClickLongItem = { }
        )
    }
}

@Preview
@Composable
private fun DailyRecordOverviewPreview() {
    SeeDayTheme {
        DailyRecordOverview(
            recordId = "",
            dailyEmotion = DailyEmotion.Normal,
            recordDate = "오전 12:18",
            content = "오늘 아침에 일어나서 밥먹고 소화시키다 누워서 다시 자고 일어나서 다시 밥먹고 다시 자고나니 하루가 다 갔다.",
            photoUrls = listOf(),
            onClickItem = { recordType, recordId -> },
            onClickLongItem = {  }
        )
    }
}
