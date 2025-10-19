package see.day.habit.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray30
import see.day.designsystem.util.getIconRes
import see.day.habit.R
import see.day.habit.component.HabitAlertComponent
import see.day.habit.component.HabitSelectBottomSheet
import see.day.habit.state.HabitRecordPostType
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitType
import see.day.ui.button.CompleteButton
import see.day.ui.component.TypeTitle
import see.day.ui.textField.RecordWriteTextField
import see.day.ui.topbar.DetailRecordTopBar
import see.day.ui.topbar.EditMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HabitDetailScreenRoot(
    habitRecordPostType: HabitRecordPostType,
) {
    if (habitRecordPostType is HabitRecordPostType.Write) {
        var habitType by remember { mutableStateOf(habitRecordPostType.habitType) }
        var openSelectEmotionDialog by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()

        if (openSelectEmotionDialog) {
            HabitSelectBottomSheet(
                sheetState = sheetState,
                onDismiss = { openSelectEmotionDialog = false },
                onClickHabit = { newHabitType ->
                    habitType = newHabitType
                }
            )
        }
        HabitDetailScreen(
            habitType = habitType,
            onClickHabitTitle = { openSelectEmotionDialog = true }
        )
    }

}

@Composable
internal fun HabitDetailScreen(
    modifier: Modifier = Modifier,
    habitType: HabitType,
    onClickHabitTitle: () -> Unit
) {
    val (isChecked, onClickAlertSwitch) = remember { mutableStateOf(false) }
    var hour by remember { mutableStateOf(10) }
    var minute by remember { mutableStateOf(0) }
    val onChangedSpinner: (Int, Int) -> Unit = { newHour, newMinute ->
        hour = newHour
        minute = newMinute
    }

    val (memo, onMemoChanged) = remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            DetailRecordTopBar(
                recordType = RecordType.HABIT,
                editMode = EditMode.ADD,
                onClickCloseButton = {},
                onClickDeleteButton = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TypeTitle(
                modifier = modifier.padding(top = 10.dp),
                typeIcon = habitType.getIconRes,
                typeName = habitType.displayName,
                onClickType = onClickHabitTitle
            )
            HabitAlertComponent(
                modifier = modifier
                    .padding(top = 24.dp),
                isChecked = isChecked,
                hour = hour,
                minute = minute,
                onClickSwitch = onClickAlertSwitch,
                onTimeChanged = onChangedSpinner
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .border(1.dp, gray30)
            )
            Row(
                modifier = Modifier.padding(top = 24.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_article),
                    contentDescription = stringResource(R.string.memo),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(R.string.memo),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            RecordWriteTextField(
                modifier = Modifier.padding(top = 10.dp),
                placeHolder = R.string.memo,
                text = memo,
                onChangedText = onMemoChanged
            )
            CompleteButton(
                modifier = Modifier
                    .padding(top = 80.dp)
                    .systemBarsPadding(),
                text = "작성하기",
                isEnabled = true,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun HabitDetailScreenPreview() {
    SeeDayTheme {
        HabitDetailScreen(habitType = HabitType.SAVING, onClickHabitTitle = {})
    }
}
