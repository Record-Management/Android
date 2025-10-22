package see.day.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray60
import see.day.ui.R

@Composable
fun NicknameChangedComponent(
    modifier: Modifier = Modifier,
    nickname: String,
    onChangedNickname: (String) -> Unit
) {
    val isError by remember(nickname) { mutableStateOf(nickname.isNotValidNickname()) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = nickname,
            onValueChange = onChangedNickname,
            textStyle = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = gray20,
                focusedContainerColor = gray20,
                errorContainerColor = gray20,
                disabledContainerColor = gray20,
                disabledPlaceholderColor = gray20,
                focusedPlaceholderColor = gray20,
                unfocusedPlaceholderColor = gray20,
                disabledIndicatorColor = gray20,
                focusedIndicatorColor = gray20,
                errorIndicatorColor = MaterialTheme.colorScheme.onError,
                unfocusedIndicatorColor = gray20
            ),
            isError = isError,
            placeholder = {
                Text(
                    text = stringResource(R.string.nickname_hint),
                    style = MaterialTheme.typography.displayMedium,
                    color = gray50
                )
            },
            trailingIcon = {
                if (nickname.isNotEmpty()) {
                    IconButton(onClick = { onChangedNickname("") }) {
                        Image(
                            painter = painterResource(R.drawable.ic_close_circle),
                            contentDescription = "닉네임 지우기 버튼",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            },
            singleLine = true
        )

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(R.string.nickname_description),
            style = MaterialTheme.typography.headlineMedium,
            color = if (isError) MaterialTheme.colorScheme.onError else gray60
        )
    }
}

private fun String.isNotValidNickname(): Boolean {
    val regex = Regex("^[a-zA-Z가-힣]{0,6}\$")
    return !regex.matches(this)
}

@Preview
@Composable
private fun NicknameChangedComponentPreview() {
    val (nickname, onChangedNickname) = remember { mutableStateOf("") }
    SeeDayTheme {
        NicknameChangedComponent(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp,top = 10.dp).fillMaxWidth(),
            nickname = nickname,
            onChangedNickname = onChangedNickname
        )
    }
}
