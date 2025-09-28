package see.day.daily.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.util.largeIconRes
import see.day.model.record.daily.DailyEmotion
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter

@Composable
internal fun EmotionAndDate(
    modifier: Modifier = Modifier,
    emotion: DailyEmotion,
    currentDate: String,
    currentTime: String,
    onClickEmotion : () -> Unit
) {
    Row(
        modifier = modifier.padding(top = 10.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            painter = painterResource(emotion.largeIconRes),
            contentDescription = "emotion ${emotion.name}",
            modifier = modifier
                .size(80.dp)
                .clickable { onClickEmotion() },
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = modifier
                .padding(vertical = (14.5).dp)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = currentDate,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = currentTime,
                style = MaterialTheme.typography.displayLarge,
                modifier = modifier.padding(top = 3.dp)
            )
        }
    }
}

@Preview
@Composable
private fun EmotionAndDatePreview() {
    SeeDayTheme {
        EmotionAndDate(
            emotion = DailyEmotion.Tired,
            currentDate = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)).formatFullDate(),
            currentTime = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)).formatFullTime(),
            onClickEmotion = {}
        )
    }
}
