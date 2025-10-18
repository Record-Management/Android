package see.day.habit.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.habit.R
import see.day.habit.component.HabitTypeCard
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitType
import see.day.ui.dialog.RecordTypePickerDialog
import see.day.ui.topbar.RecordSelectTopBar

@Composable
fun HabitSelectScreenRoot(
    onClickChangedRecordType: (RecordType, Boolean) -> Unit,
    onBack: () -> Unit,
    onClickHabitType: (HabitType) -> Unit
) {
    HabitSelectScreen(
        onClickChangedRecordType = onClickChangedRecordType,
        onBack = onBack,
        onClickHabitType = onClickHabitType
    )
}

@Composable
internal fun HabitSelectScreen(
    modifier: Modifier = Modifier,
    onClickChangedRecordType: (RecordType, Boolean) -> Unit,
    onBack: () -> Unit,
    onClickHabitType: (HabitType) -> Unit
) {
    var isOpenRecordTypePickerDialog by remember { mutableStateOf(false) }

    if (isOpenRecordTypePickerDialog) {
        RecordTypePickerDialog(
            currentRecordType = RecordType.HABIT,
            onDismiss = { isOpenRecordTypePickerDialog = false },
            onCompleteRecordType = { selectedType ->
                onClickChangedRecordType(selectedType, true)
                isOpenRecordTypePickerDialog = false
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            RecordSelectTopBar(recordType = RecordType.HABIT, onBack = onBack)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(HabitType.entries, key = { it }) { habitType ->
                HabitTypeCard(habitType = habitType, onClickHabitType = onClickHabitType)
            }
            item {
                Text(
                    text = stringResource(R.string.change_record_type),
                    style = MaterialTheme.typography.labelMedium.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                        .clickable {
                            isOpenRecordTypePickerDialog = true
                        },
                    textAlign = TextAlign.Center
                )
            }
            item {
                Spacer(modifier = modifier.systemBarsPadding())
            }
        }
    }
}

@Preview
@Composable
private fun HabitSelectScreenPreview() {
    SeeDayTheme {
        HabitSelectScreen(
            onClickChangedRecordType = { type, boolean -> },
            onBack = {},
            onClickHabitType = {}
        )
    }
}
