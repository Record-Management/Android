package see.day.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme
import see.day.home.component.HomeImage

@Composable
fun HomeScreenRoot(
    modifier : Modifier = Modifier
) {
    HomeScreen(
        modifier
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HomeImage(modifier)
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    SeeDayTheme {
        HomeScreen()
    }
}
