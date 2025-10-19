package see.day.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.util.getIconRes
import see.day.model.record.exercise.ExerciseType
import see.day.model.record.habit.HabitType

@Composable
fun TypeTitle(
    modifier: Modifier = Modifier,
    typeIcon: Int,
    typeName: String,
    onClickType: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(typeIcon),
            contentDescription = typeName,
            modifier = Modifier
                .size(100.dp)
                .clickable { onClickType() }
        )
        Text(
            text = typeName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 24.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun ExerciseTypeTitlePreview() {
    val type = ExerciseType.GOLF
    SeeDayTheme {
        TypeTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            typeIcon = type.getIconRes,
            typeName = type.displayName,
            onClickType = {}
        )
    }
}

@Preview
@Composable
private fun HabitTypeTitlePreview() {
    val type = HabitType.SAVING
    SeeDayTheme {
        TypeTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            typeIcon = type.getIconRes,
            typeName = type.displayName,
            onClickType = {}
        )
    }
}
