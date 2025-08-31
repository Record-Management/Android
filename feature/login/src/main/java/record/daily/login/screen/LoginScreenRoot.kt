package record.daily.login.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import record.daily.login.social.kakaoLogin
import timber.log.Timber

@Composable
internal fun LoginScreenRoot(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LoginScreen(
        modifier = modifier,
        onClickKakaoLogin = {
            kakaoLogin(context, { socialId ->
                Timber.i("socialId = $socialId")
            })
        }
    )
}

@Composable
internal fun LoginScreen(modifier: Modifier = Modifier, onClickKakaoLogin: () -> Unit) {
    Column(
        modifier = modifier
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
