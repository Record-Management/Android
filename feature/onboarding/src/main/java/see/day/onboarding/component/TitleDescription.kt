package see.day.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.onboarding.state.onboarding.OnboardingUiState

@Composable
internal fun TitleDescription(
    modifier: Modifier = Modifier,
    uiState: OnboardingUiState
) {
    Image(
        modifier = modifier.padding(top = 30.dp, start = 16.dp),
        painter = painterResource(uiState.onboardingScreenState.iconRes),
        contentDescription = "온보딩 ${uiState.onboardingScreenState.ordinal} 번째 아이콘"
    )

    Text(
        modifier = modifier.padding(top = 10.dp, start = 16.dp),
        text = stringResource(uiState.onboardingScreenState.titleRes),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview
@Composable
private fun TitleDescriptionPreview() {
    SeeDayTheme {
        TitleDescription(
            uiState = OnboardingUiState.init
        )
    }
}
