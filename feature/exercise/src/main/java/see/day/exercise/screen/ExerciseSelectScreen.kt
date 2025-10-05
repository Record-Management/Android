package see.day.exercise.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.exercise.R
import see.day.exercise.component.ExerciseSelectTopBar
import see.day.model.record.RecordType
import see.day.ui.dialog.RecordTypePickerDialog

@Composable
fun ExerciseSelectScreenRoot(
    onClickChangeRecordType: (RecordType, Boolean) -> Unit,
    onBack: () -> Unit
) {
    ExerciseSelectScreen(
        onClickChangeRecordType = onClickChangeRecordType,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExerciseSelectScreen(
    modifier: Modifier = Modifier,
    onClickChangeRecordType: (RecordType, Boolean) -> Unit,
    onBack: () -> Unit
) {
    var isOpenRecordTypePickerDialog by remember { mutableStateOf(false) }

    if (isOpenRecordTypePickerDialog) {
        RecordTypePickerDialog(
            currentRecordType = RecordType.EXERCISE,
            onDismiss = { isOpenRecordTypePickerDialog = false },
            onCompleteRecordType = { selectedType ->
                onClickChangeRecordType(selectedType, true)
                isOpenRecordTypePickerDialog = false
            }
        )
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            ExerciseSelectTopBar(modifier, onBack)
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Button(
                    { isOpenRecordTypePickerDialog = true }
                ) {
                    Text("기록 변경")
                }
            }
            item {
                Button(
                    { isOpenRecordTypePickerDialog = true }
                ) {
                    Text("기록 변경")
                }
            }
        }

    }
}

@Preview
@Composable
private fun ExerciseSelectScreenPreview() {
    SeeDayTheme {
        ExerciseSelectScreen(
            onClickChangeRecordType = { type, boolean -> },
            onBack = {}
        )
    }
}
