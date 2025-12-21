package see.day.onboarding.screen.onboarding

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.onboarding.R
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.ui.button.CompleteButton

@Composable
internal fun AlertScreen(modifier: Modifier = Modifier, onClickComplete: (OnboardingUiEvent) -> Unit) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {

    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.alert_image),
            contentDescription = "알람 요청 이미지",
            modifier = modifier.fillMaxWidth().offset(x = 16.dp),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = modifier.weight(1f))

        CompleteButton(
            modifier = modifier,
            text = "다음",
            isEnabled = true,
            onClick = { onClickComplete(OnboardingUiEvent.OnClickFinishOnboarding) }
        )
    }
}

@Preview
@Composable
private fun AlertScreenPreview() {
    SeeDayTheme {
        AlertScreen {
        }
    }
}
