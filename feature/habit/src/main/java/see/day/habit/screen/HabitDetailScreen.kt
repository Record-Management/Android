package see.day.habit.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.util.getIconRes
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitType
import see.day.ui.topbar.DetailRecordTopBar
import see.day.ui.topbar.EditMode

@Composable
internal fun HabitDetailScreenRoot(
    habitType: HabitType
) {
    HabitDetailScreen(
        habitType = habitType
    )
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
            Image(
                painter = painterResource(habitType.getIconRes),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
            Text(
                habitType.displayName
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
