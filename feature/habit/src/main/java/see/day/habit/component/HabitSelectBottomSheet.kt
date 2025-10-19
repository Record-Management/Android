package see.day.habit.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.record.habit.HabitType
import see.day.navigation.habit.HabitRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HabitSelectBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onClickHabit: (HabitType) -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        dragHandle = {
            Box(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "습관 재선택",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = modifier.align(Alignment.Center)
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .clickable { onDismiss() },
                    painter = painterResource(see.day.ui.R.drawable.ic_close),
                    contentDescription = "뒤로가기 버튼"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(HabitType.entries, key = { it }) { habitType ->
                HabitTypeCard(
                    habitType = habitType,
                    onClickHabitType = {
                        onClickHabit(habitType)
                        onDismiss()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HabitSelectBottomPreview() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    SeeDayTheme {
        HabitSelectBottomSheet(
            sheetState = sheetState,
            onDismiss = {},
            onClickHabit = {}
        )
    }
}
