package record.daily.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import record.daily.login.R
import see.day.designsystem.theme.SeeDayTheme

@Composable
internal fun ColumnScope.SplashScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_title),
            contentDescription = "splash_title"
        )
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "splash_image"
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SeeDayTheme {
        Column {
            SplashScreen()
        }
    }
}
