package see.day.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.setting.R
import see.day.ui.button.CompleteButton
import see.day.ui.component.NicknameChangedComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NicknameChangedBottomSheet(
    modifier: Modifier = Modifier,
    nickname: String,
    onDismiss: () -> Unit,
    onNicknameChanged: (String) -> Unit
) {
    val (nicknameValue, onNicknameValueChanged) = remember { mutableStateOf(nickname) }
    val isError by remember(nicknameValue) { mutableStateOf(nicknameValue.isNotValidNickname()) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        dragHandle = {
            Box(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.change_nickname),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.Center)
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onDismiss() },
                    painter = painterResource(see.day.ui.R.drawable.ic_close),
                    contentDescription = "종료 버튼"
                )
            }
        }
    ) {
        Column(modifier = modifier) {
            NicknameChangedComponent(
                modifier = Modifier.padding(top = 10.dp),
                nickname = nicknameValue,
                onChangedNickname = onNicknameValueChanged
            )
            CompleteButton(
                modifier = Modifier.padding(top = 24.dp, bottom = 12.dp).systemBarsPadding(),
                text = "변경하기",
                isEnabled = !isError && nicknameValue.isNotEmpty() && nicknameValue != nickname,
                onClick = {onNicknameChanged(nicknameValue)}
            )
        }

    }
}

private fun String.isNotValidNickname(): Boolean {
    val regex = Regex("^[a-zA-Z가-힣]{0,6}\$")
    return !regex.matches(this)
}

@Preview
@Composable
private fun NicknameChangedBottomSheetPreview() {
    val (nickname, onNicknameChanged) = remember { mutableStateOf("변하기전") }
    SeeDayTheme {
        NicknameChangedBottomSheet(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            nickname = nickname,
            onDismiss = {},
            onNicknameChanged = {}
        )
    }
}
