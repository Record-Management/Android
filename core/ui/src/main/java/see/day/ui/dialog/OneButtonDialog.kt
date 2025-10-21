package see.day.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray70
import see.day.ui.R

@Composable
fun OneButtonDialog(
    modifier: Modifier = Modifier,
    titleRes: Int,
    bodyRes: Int,
    onDismiss: () -> Unit,
    onClickAcceptButton: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        DialogBackground(
            modifier = modifier,
            onDismiss = onDismiss
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 33.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .clickable(onClick = {}, indication = null, interactionSource = remember { MutableInteractionSource() })
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(titleRes),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(bodyRes),
                        style = MaterialTheme.typography.labelSmall.copy(color = gray70)
                    )
                    Button(
                        onClick = onClickAcceptButton,
                        modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "확인",
                            style = MaterialTheme.typography.displayLarge.copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OneButtonDialogPreview() {
    SeeDayTheme {
        OneButtonDialog(
            titleRes = R.string.today_records_over_title,
            bodyRes = R.string.today_records_over_body,
            onDismiss = {},
            onClickAcceptButton = {}
        )
    }
}
