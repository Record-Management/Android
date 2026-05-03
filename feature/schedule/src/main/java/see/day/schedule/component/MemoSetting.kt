package see.day.schedule.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray60
import see.day.schedule.R

@Composable
internal fun MemoSetting(modifier: Modifier = Modifier, text: String, onChangedText: (String) -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            painter = painterResource(R.drawable.ic_article),
            contentDescription = stringResource(R.string.memo),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(R.string.memo),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 6.dp)
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(251.dp)
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(gray20)
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                if(newValue.length <= 1000) {
                    onChangedText(newValue)
                }
            },
            textStyle = MaterialTheme.typography.displayMedium.copy(color = gray100),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
                .padding(top = 14.dp, bottom = 45.dp),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = "메모",
                        style = MaterialTheme.typography.displayMedium,
                        color = gray50
                    )
                }
                innerTextField()
            }
        )
        Text(
            text = "${text.length} / 1000",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 14.dp, bottom = 14.dp),
            color = if (text.isEmpty()) gray60 else gray100
        )
    }
}
