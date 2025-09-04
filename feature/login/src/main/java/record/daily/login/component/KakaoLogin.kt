package record.daily.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import record.daily.login.R
import see.day.designsystem.theme.SeeDayTheme

@Composable
internal fun KakaoLogin(modifier: Modifier = Modifier, onClickKakaoLogin: () -> Unit) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClickKakaoLogin() },
        painter = painterResource(id = R.drawable.kakao_login),
        contentDescription = "kakao_login",
        contentScale = ContentScale.FillWidth
    )
}

@Preview
@Composable
private fun KakaoLoginPreview() {
    SeeDayTheme {
        KakaoLogin(
            onClickKakaoLogin = {}
        )
    }
}
