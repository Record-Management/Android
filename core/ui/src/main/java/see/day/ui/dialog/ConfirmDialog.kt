package see.day.ui.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray70
import see.day.designsystem.theme.primaryColor
import see.day.ui.R

@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    @StringRes title: Int = R.string.record_delete_title,
    @StringRes body: Int = R.string.record_delete_body,
    @StringRes cancel: Int = R.string.record_dismiss_button_text,
    @StringRes confirm: Int = R.string.record_delete_button_text,
    onDismiss: () -> Unit,
    onClickConfirmButton: () -> Unit,
    confirmButtonColor: Color = primaryColor
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
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(title),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        modifier = modifier.padding(top = 8.dp),
                        text = stringResource(body),
                        style = MaterialTheme.typography.labelSmall.copy(color = gray70)
                    )
                    Row(
                        modifier = modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                onDismiss()
                            },
                            modifier = modifier
                                .weight(1f)
                                .heightIn(min = 52.dp)
                                .padding(end = 5.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = gray20
                            )
                        ) {
                            Text(
                                stringResource(cancel),
                                color = gray50,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                        Button(
                            onClick = {
                                onClickConfirmButton()
                                onDismiss()
                            },
                            modifier = modifier
                                .weight(1f)
                                .heightIn(min = 52.dp)
                                .padding(start = 5.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = confirmButtonColor
                            )
                        ) {
                            Text(
                                stringResource(confirm),
                                color = Color.White,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ConfirmDialogPreview() {
    SeeDayTheme {
        ConfirmDialog(
            onDismiss = {},
            onClickConfirmButton = {}
        )
    }
}
