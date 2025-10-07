package see.day.ui.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray50

@Composable
fun HealthStatInputField(
    modifier: Modifier = Modifier,
    healthStat: HealthStat,
    text: String,
    onTextChanged: (String) -> Unit,
    focusManager: FocusManager
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            healthStat.title,
            style = MaterialTheme.typography.displaySmall
        )
        BasicTextField(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(gray20)
                .padding(14.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = if (isFocused) text else "$text ${healthStat.unit}",
            textStyle = MaterialTheme.typography.displaySmall,
            onValueChange = { newValue ->
                if (newValue.isDigitsOnly()) {
                    onTextChanged(newValue)
                }
            },
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = "0 ${healthStat.unit}",
                        style = MaterialTheme.typography.displaySmall,
                        color = gray50
                    )
                    return@BasicTextField
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            maxLines = 1,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
        )
    }
}

enum class HealthStat(val title: String, val unit: String) {
    Kcal("소모 칼로리", "Kcal"), Time("운동 시간", "분"), StepCount("걸음 수", "걸음"), Weight("몸무게", "Kg")
}

@Preview
@Composable
private fun HealthStatInputFieldPreview() {
    val (text, onTextChanged) = remember { mutableStateOf("") }
    SeeDayTheme {
        HealthStatInputField(
            modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp),
            healthStat = HealthStat.Time,
            text = text,
            onTextChanged = onTextChanged,
            focusManager = LocalFocusManager.current
        )
    }
}
