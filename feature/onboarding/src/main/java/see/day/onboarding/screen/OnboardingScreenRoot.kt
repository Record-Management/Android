package see.day.onboarding.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme

@Composable
internal fun OnboardingScreenRoot() {
    OnboardingScreen()
}

@Composable
internal fun OnboardingScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.systemBarsPadding()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text("온보딩")
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    SeeDayTheme {
        OnboardingScreen()
    }
}
