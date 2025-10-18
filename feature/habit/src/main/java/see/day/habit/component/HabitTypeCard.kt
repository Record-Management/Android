package see.day.habit.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray10
import see.day.designsystem.util.getIconRes
import see.day.model.record.habit.HabitType

@Composable
internal fun HabitTypeCard(
    modifier: Modifier = Modifier,
    habitType: HabitType,
    onClickHabitType: (HabitType) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(gray10)
            .clickable { onClickHabitType(habitType) }
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(habitType.getIconRes),
            contentDescription = habitType.displayName,
            modifier = modifier.size(50.dp)
                .align(Alignment.CenterStart)
        )
        Text(
            text = habitType.displayName,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.align(Alignment.Center)
        )
        Image(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = "선택 버튼",
            modifier = modifier.size(20.dp).align(Alignment.CenterEnd)
        )
    }
}

@Preview
@Composable
private fun HabitTypeCardPreview() {
    SeeDayTheme {
        HabitTypeCard(habitType = HabitType.SAVING, onClickHabitType = {})
    }
}
