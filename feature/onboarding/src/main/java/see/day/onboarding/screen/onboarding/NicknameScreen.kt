package see.day.onboarding.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.ui.button.CompleteButton
import see.day.ui.component.NicknameChangedComponent

@Composable
internal fun NicknameScreen(modifier: Modifier = Modifier, nickname: String, onComplete: (OnboardingUiEvent) -> Unit) {
    val (nicknameValue, onNicknameChanged) = remember { mutableStateOf(nickname) }
    val isError by remember(nicknameValue) { mutableStateOf(nicknameValue.isNotValidNickname()) }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        NicknameChangedComponent(
            modifier = modifier,
            nickname = nicknameValue,
            onChangedNickname = onNicknameChanged
        )

        Spacer(modifier = modifier.weight(1f))

        CompleteButton(
            modifier = modifier.imePadding(),
            text = "다음",
            isEnabled = !isError && nicknameValue.isNotEmpty(),
            onClick = { onComplete(OnboardingUiEvent.EnterNickname(nicknameValue)) }
        )
    }
}

private fun String.isNotValidNickname(): Boolean {
    val regex = Regex("^[a-zA-Z가-힣]{0,6}\$")
    return !regex.matches(this)
}

@Preview
@Composable
private fun NicknameScreenPreview() {
    SeeDayTheme {
        NicknameScreen(
            nickname = "asdsad",
            onComplete = {}
        )
    }
}
