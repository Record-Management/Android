package see.day.onboarding.screen

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import see.day.designsystem.theme.SeeDayTheme
import see.day.navigation.onboarding.CompleteType
import see.day.onboarding.R
import see.day.onboarding.component.OnboardingCompleteLabel
import see.day.onboarding.util.isNotificationPermissionGranted
import see.day.ui.button.CompleteButton

@Composable
internal fun OnboardingCompleteScreenRoot(modifier: Modifier = Modifier, completeType: CompleteType, onGoHome: () -> Unit) {
    val context = LocalContext.current

    var titleText by rememberSaveable {
        mutableStateOf("하루를 담을 준비를 하고 있어요!")
    }
    LaunchedEffect(Unit) {

        delay(2100)
        titleText = "하루를 채울 준비를 마쳤어요!"
    }

    when (completeType) {
        CompleteType.ONBOARDING -> {
            if (!isNotificationPermissionGranted(context)) {
                Toast.makeText(context, stringResource(R.string.notification_setting_denied), Toast.LENGTH_SHORT).show()
            }
        }

        CompleteType.RESET_GOAL -> {

        }
    }

    OnboardingCompleteScreen(
        modifier = modifier,
        titleText = titleText,
        onGoHome = onGoHome
    )
}

@Composable
internal fun OnboardingCompleteScreen(modifier: Modifier = Modifier, titleText: String, onGoHome: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .verticalScroll(scrollState)
    ) {
        Image(
            modifier = Modifier
                .padding(top = 66.dp)
                .systemBarsPadding()
                .height(height = 216.dp)
                .padding(horizontal = 31.dp)
                .fillMaxWidth(),
            painter = painterResource(R.drawable.onboard_complete),
            contentDescription = "온보딩 완료 이미지",
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier
                .padding(top = 28.dp)
                .fillMaxWidth(),
            text = titleText,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(51.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            labelList().forEachIndexed { index, labelText ->
                FadeInLabel(
                    labelText = labelText,
                    delayMills = index * WAIT_TIME
                )
            }
        }


        Spacer(modifier = Modifier.weight(1f))
        FadeEffect(2100, scrollState = scrollState) { modifier ->
            CompleteButton(
                modifier = modifier.navigationBarsPadding(),
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

    LaunchedEffect(Unit) {
        delay(delayMills.toLong())
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = WAIT_TIME,
                easing = LinearEasing
            )
        )
    }

    OnboardingCompleteLabel(
        labelText = labelText,
        modifier = Modifier.alpha(alpha.value)
    )
}

@Composable
fun FadeEffect(delayMills: Int, scrollState: ScrollState, content: @Composable (modifier: Modifier) -> Unit) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(delayMills.toLong())
        withContext(Dispatchers.Main) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
        withContext(Dispatchers.Main) {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = WAIT_TIME,
                    easing = LinearEasing
                )
            )
        }
    }

    content(Modifier.alpha(alpha.value))
}

@Preview
@Composable
private fun OnboardingCompleteScreenPreview() {
    var titleText by rememberSaveable {
        mutableStateOf("하루를 담을 준비를 하고 있어요!")
    }
    LaunchedEffect(Unit) {

        delay(2100)
        titleText = "하루를 채울 준비를 마쳤어요!"
    }

    SeeDayTheme {
        OnboardingCompleteScreen(
            titleText = titleText,
            onGoHome = {}
        )
    }
}

private fun labelList(): List<Int> {
    return listOf(R.string.onboard_complete_label_1, R.string.onboard_complete_label_2, R.string.onboard_complete_label_3)
}

const val WAIT_TIME = 700
