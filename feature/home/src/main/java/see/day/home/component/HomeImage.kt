package see.day.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme
import see.day.home.R

@Composable
internal fun BoxScope.HomeImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.home_screen_background),
        contentDescription = "홈 화면 이미지",
        modifier = modifier
            .align(Alignment.Center)
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Preview
@Composable
private fun HomeImagePreview() {
    SeeDayTheme {
        Box() {
            HomeImage()
        }
    }
}
