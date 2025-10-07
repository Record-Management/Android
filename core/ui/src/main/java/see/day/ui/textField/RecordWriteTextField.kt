package see.day.ui.textField

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray60
import see.day.ui.R

@Composable
fun RecordWriteTextField(modifier: Modifier = Modifier, text: String, @StringRes placeHolder: Int, onChangedText: (String) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(251.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(gray20)
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                onChangedText(newValue)
            },
            textStyle = MaterialTheme.typography.displayMedium.copy(color = gray100),
            modifier = Modifier.fillMaxSize().padding(horizontal = 14.dp).padding(top = 14.dp, bottom = 45.dp),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = stringResource(placeHolder),
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

@Preview
@Composable
private fun RecordWriteTextFieldPreview() {
    val (text, onChangedText) = remember { mutableStateOf("") }
    SeeDayTheme {
        RecordWriteTextField(
            text = text,
            placeHolder = R.string.daily_place_holder,
            onChangedText = onChangedText
        )
    }
}
