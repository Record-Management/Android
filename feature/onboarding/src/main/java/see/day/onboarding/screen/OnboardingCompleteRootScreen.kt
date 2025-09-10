package see.day.onboarding.screen

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import see.day.designsystem.theme.SeeDayTheme
import see.day.onboarding.R
import see.day.onboarding.component.NextButton
import see.day.onboarding.component.OnboardingCompleteLabel
import see.day.onboarding.util.isNotificationPermissionGranted

@Composable
internal fun OnboardingCompleteScreenRoot(
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit
) {
    val context = LocalContext.current

    if(!isNotificationPermissionGranted(context)) {
        Toast.makeText(context, stringResource(R.string.notification_setting_denied), Toast.LENGTH_SHORT).show()
    }

    OnboardingCompleteScreen(
        modifier = modifier,
        onGoHome = onGoHome
    )
}

@Composable
internal fun OnboardingCompleteScreen(
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit
) {
    var showNextButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay((labelList().size * waitTime).toLong())
        showNextButton = true
    }
    Column {
        Image(
            modifier = modifier
                .padding(top = 66.dp)
                .systemBarsPadding()
                .height(height = 216.dp)
                .padding(horizontal = 47.dp)
                .fillMaxWidth(),
            painter = painterResource(R.drawable.onboard_complete),
            contentDescription = "온보딩 완료 이미지",
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = modifier
                .padding(top = 28.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.onboard_complete_title_message),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(37.dp))
        labelList().forEachIndexed { index, labelText ->
            FadeInLabel(
                labelText = labelText,
                delayMills = index * waitTime
            )
        }

        if(showNextButton) {
            Spacer(modifier = modifier.weight(1f))
            NextButton(
                isEnabled = true,
                text = "시작하기",
                onClick = onGoHome
            )
        }
    }
}

@Composable
fun FadeInLabel(@StringRes labelText: Int, delayMills: Int) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect (Unit) {
        delay(delayMills.toLong())
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = waitTime,
                easing = LinearEasing
            )
        )
    }

    OnboardingCompleteLabel(
        labelText = labelText,
        modifier = Modifier.alpha(alpha.value)
    )
}

@Preview
@Composable
private fun OnboardingCompleteScreenPreview() {
    SeeDayTheme {
        OnboardingCompleteScreen(
            onGoHome = {}
        )
    }
}

private fun labelList() :List<Int> {
    return listOf(R.string.onboard_complete_label_1,R.string.onboard_complete_label_2,R.string.onboard_complete_label_3)
}

const val waitTime = 700
