package see.day.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.record.RecordType
import see.day.ui.button.CompleteButton
import see.day.ui.card.GoalRecordTypeCard

@Composable
fun RecordTypeScreen(modifier: Modifier = Modifier, selectedRecordType: RecordType?, onClickCompleteButton: (RecordType) -> Unit) {
    var currentSelectedRecordType by rememberSaveable {
        mutableStateOf(selectedRecordType)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RecordType.entries.forEach { recordType ->
            GoalRecordTypeCard (
                modifier = modifier,
                recordType = recordType,
                isClicked = currentSelectedRecordType?.let { it == recordType } ?: false,
                onClickItem = { type ->
                    if (type == currentSelectedRecordType) {
                        currentSelectedRecordType = null
                    } else {
                        currentSelectedRecordType = type
                    }
                }
            )
        }
        Spacer(modifier = modifier.weight(1f))
        CompleteButton (
            modifier = modifier,
            text = "다음",
            isEnabled = currentSelectedRecordType != null,
            onClick = {
                currentSelectedRecordType?.let { recordType ->
                    onClickCompleteButton(recordType)
                }
            }
        )
    }
}

@Preview
@Composable
private fun RecordTypeScreenPreview() {
    SeeDayTheme {
        RecordTypeScreen(
            selectedRecordType = RecordType.DAILY,
            onClickCompleteButton = {}
        )
    }
}
