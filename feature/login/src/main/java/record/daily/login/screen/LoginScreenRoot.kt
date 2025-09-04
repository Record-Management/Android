package record.daily.login.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import record.daily.login.component.KakaoLogin
import record.daily.login.component.SplashScreen
import record.daily.login.social.kakaoLogin
import see.day.designsystem.theme.SeeDayTheme
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
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SplashScreen(modifier = modifier)
        KakaoLogin(
            modifier = modifier,
            onClickKakaoLogin = onClickKakaoLogin
        )
        Spacer(modifier = modifier.padding(bottom = 72.dp))
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    SeeDayTheme {
        LoginScreen(
            onClickKakaoLogin = {}
        )
    }
}
