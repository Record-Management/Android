package record.daily.login.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    onClickKakaoLogin: () -> Unit,
) {
    LoginScreen(
        modifier = modifier,
        onClickKakaoLogin = onClickKakaoLogin
    )
}

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    onClickKakaoLogin: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Text("hello LoginScreen")

        Button(onClickKakaoLogin) {
            Text("Kakao Login")
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onClickKakaoLogin = {}
    )
}
