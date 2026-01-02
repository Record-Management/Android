package see.day.ui.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray50

@Composable
fun GrayButton(
    modifier: Modifier = Modifier, text: String, isEnabled: Boolean, onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled,
        colors = ButtonColors(
            containerColor = gray20,
            contentColor = gray20,
            disabledContainerColor = gray20,
            disabledContentColor = gray20
        )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
            color = if (isEnabled) gray100 else gray50
        )
    }
}

@Preview
@Composable
private fun GrayButtonPreview() {
    SeeDayTheme {
        GrayButton(
            text = "완료",
            isEnabled = true,
            onClick = {}
        )
    }
}
