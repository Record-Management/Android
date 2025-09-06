package record.daily.login.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import record.daily.login.component.KakaoLogin
import record.daily.login.component.SplashScreen
import record.daily.login.social.kakaoLogin
import record.daily.login.state.login.LoginUiEffect
import record.daily.login.viewmodel.LoginViewModel
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.login.SocialType

@Composable
internal fun LoginScreenRoot(
    viewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onGoOnboarding: () -> Unit,
    onGoHome: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                LoginUiEffect.GoOnboarding -> {
                    onGoOnboarding()
                }

                LoginUiEffect.GoHome -> {
                    onGoHome
                }
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        onClickKakaoLogin = {
            kakaoLogin(context) { token ->
                viewModel.login(SocialType.KAKAO, token)
            }
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
