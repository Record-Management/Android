package see.day.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.home.R

@Composable
internal fun SelectedDateComponent(
    modifier: Modifier = Modifier,
    currentYear: Int,
    currentMonth: Int,
    isDateSelectMode: Boolean,
    onClickDate: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClickDate(!isDateSelectMode) }
    ) {
        Text(
            text = "$currentYear.$currentMonth",
            style = MaterialTheme.typography.bodyMedium
        )
        Image(
            painter = painterResource(R.drawable.ic_arrow_down),
            contentDescription = "아래꺽쇠 아이콘",
            modifier = modifier
                .size(20.dp)
                .padding(start = 2.dp)
        )
    }
}

@Preview
@Composable
private fun SelectedDateComponentPreview() {
    SeeDayTheme {
        SelectedDateComponent(
            currentYear = 2025,
            currentMonth = 10,
            isDateSelectMode = false,
            onClickDate = {}
        )
    }
}
