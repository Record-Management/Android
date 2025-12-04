package see.day.onboarding.screen.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray30
import see.day.designsystem.theme.gray80
import see.day.designsystem.theme.primaryColor
import see.day.onboarding.R
import see.day.ui.button.CompleteButton

@Composable
internal fun TermsScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var termCheck by remember { mutableStateOf(false) }
    var termsExpended by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.background(Color.White)
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp)
            .systemBarsPadding()
    ) {
        Text(
            modifier = Modifier.padding(top = 66.dp),
            text = stringResource(R.string.terms_title),
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            modifier = Modifier
                .padding(top = 24.dp)
                .clickable { termCheck = !termCheck }
                .fillMaxWidth()
                .heightIn(44.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(20.dp).clip(CircleShape).background(if(termCheck) primaryColor else gray30),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = termCheck.toString(),
                    modifier = Modifier
                        .size(16.dp).align(Alignment.Center),
                    tint = Color.Unspecified
                )
            }

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.terms_desc),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = " " + stringResource(R.string.required_text),
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(if(termsExpended) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up),
                contentDescription = "확장 버튼",
                modifier = Modifier.size(20.dp).clickable {
                    termsExpended = !termsExpended
                },
                tint = Color.Unspecified
            )
        }
        AnimatedVisibility (
            visible = termsExpended,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(gray20)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(380.dp)
                    .verticalScroll(rememberScrollState()),
                text = stringResource(R.string.terms),
                style = MaterialTheme.typography.headlineMedium.copy(color = gray80)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        CompleteButton(
            isEnabled = termCheck,
            text = "확인",
            onClick = onClick
        )
    }
}

@Preview
@Composable
private fun TermsScreenPreview() {
    SeeDayTheme {
        TermsScreen(
            onClick = {}
        )
    }
}
