package see.day.habit.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.util.getIconRes
import see.day.habit.state.HabitRecordPostType
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitType
import see.day.ui.component.TypeTitle
import see.day.ui.topbar.DetailRecordTopBar
import see.day.ui.topbar.EditMode

@Composable
internal fun HabitDetailScreenRoot(
    habitRecordPostType: HabitRecordPostType,
) {
    if(habitRecordPostType is HabitRecordPostType.Write) {
        var habitType by remember { mutableStateOf(habitRecordPostType.habitType) }

        HabitDetailScreen(
            habitType = habitType
        )
    }

}

@Composable
internal fun HabitDetailScreen(
    modifier: Modifier = Modifier,
    habitType: HabitType
) {
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
                onClickType = {}
            )
        }
    }
}

@Preview
@Composable
private fun HabitDetailScreenPreview() {
    SeeDayTheme {
        HabitDetailScreen(habitType = HabitType.SAVING)
    }
}
