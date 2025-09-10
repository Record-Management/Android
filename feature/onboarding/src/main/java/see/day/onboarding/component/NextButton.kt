package see.day.onboarding.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme

@Composable
fun NextButton(modifier: Modifier = Modifier, text: String, isEnabled: Boolean, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .systemBarsPadding()
            .height(52.dp)
            .padding(horizontal = 16.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled,
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.onSecondary,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
            color = if (isEnabled) Color.White else MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun NextButtonPreview() {
    var isEnabled by remember { mutableStateOf(true) }

    SeeDayTheme {
        NextButton(
            text = "다음",
            isEnabled = isEnabled,
            onClick = {
                isEnabled = !isEnabled
            }
        )
    }
}
