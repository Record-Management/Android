package see.day.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.onboarding.R
import see.day.onboarding.state.OnboardingScreenState.RECORD
import see.day.onboarding.state.getProgress
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.onboarding.state.onboarding.OnboardingUiEvent.OnClickBack
import see.day.onboarding.state.onboarding.OnboardingUiState

@Composable
internal fun OnboardingTopBar(modifier: Modifier = Modifier, uiState: OnboardingUiState, uiEvent: (OnboardingUiEvent) -> Unit) {
    Column(
        modifier = modifier.height(60.dp)
    ) {
        if (uiState.onboardingScreenState != RECORD) {
            Image(
                modifier = modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp)
                    .clickable { uiEvent(OnClickBack) },
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = stringResource(R.string.back_button_desc)
            )
        }
        Spacer(modifier = modifier.weight(1f))
        LinearProgressIndicator(
            progress = { uiState.onboardingScreenState.getProgress() },
            modifier = modifier
                .fillMaxWidth()
                .height(4.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun OnboardingTopBarPreview() {
    SeeDayTheme {
        OnboardingTopBar(
            uiState = OnboardingUiState.init,
            uiEvent = {}
        )
    }
}
