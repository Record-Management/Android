package see.day.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import see.day.schedule.R

@Composable
internal fun ColorSetting(
    modifier: Modifier = Modifier,
    selectedColor: Color,
    onColorChange: (Color) -> Unit,
) {
    var isShowColorBottomSheet by remember { mutableStateOf(false) }

    if (isShowColorBottomSheet) {
        ColorPaletteBottomSheet(
            selectedColor = selectedColor,
            onColorSelected = onColorChange,
            onDismiss = {
                isShowColorBottomSheet = false
            }
        )
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isShowColorBottomSheet = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_palette),
            contentDescription = "색상 설정",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "색상",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(selectedColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
        }
        Icon(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "색상 설정",
            modifier = Modifier
                .size(20.dp)
                .padding(start = 6.dp),
            tint = Color.Unspecified
        )
    }
}
