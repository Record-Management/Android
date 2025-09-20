package see.day.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.record.RecordType
import see.day.ui.R
import see.day.ui.component.RecordTypeSmallComponent

@Composable
fun RecordTypePickerDialog(modifier: Modifier = Modifier, currentRecordType: RecordType, onDismiss: () -> Unit, onCompleteRecordType: (RecordType) -> Unit) {
    var selectedRecordType by remember { mutableStateOf<RecordType?>(null) }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    stringResource(R.string.change_record_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(items = RecordType.entries.toList()) { type ->
                        if (type != currentRecordType) {
                            RecordTypeSmallComponent(
                                currentRecordType = type,
                                selectedRecordType = selectedRecordType,
                                onClickRecordType = { clickedType ->
                                    selectedRecordType = clickedType
                                }
                            )
                        }
                    }
                }
                Button(
                    onClick = { onCompleteRecordType(selectedRecordType!!) },
                    enabled = selectedRecordType != null,
                    colors = ButtonDefaults.buttonColors().copy(
                        disabledContainerColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.change_record),
                        style = MaterialTheme.typography.displayLarge,
                        color = if (selectedRecordType != null) Color.White else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecordTypePickerDialogPreview() {
    var isVisible by remember { mutableStateOf(true) }
    val onDismiss = {
        isVisible = false
    }
    SeeDayTheme {
        RecordTypePickerDialog(
            currentRecordType = RecordType.DAILY,
            onDismiss = onDismiss,
            onCompleteRecordType = {
                isVisible = false
            }
        )
    }
}
