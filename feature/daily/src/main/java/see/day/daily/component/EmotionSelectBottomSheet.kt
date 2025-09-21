package see.day.daily.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import see.day.designsystem.util.DailyEmotion
import see.day.designsystem.util.largeIconRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmotionSelectBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onClickChangeEmotion: (DailyEmotion) -> Unit

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
                    "감정 선택",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = modifier.align(Alignment.Center),
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            modifier = modifier
                .padding(top = 16.dp)
                .padding(horizontal = 43.dp)
                .padding(bottom = 180.dp)
        ) {
            items(items = DailyEmotion.entries.toList(), key = { it }) { emotion ->
                Image(
                    painter = painterResource(emotion.largeIconRes),
                    contentDescription = "emotion ${emotion.name}",
                    modifier = modifier
                        .size(80.dp)
                        .clickable {
                            onClickChangeEmotion(emotion)
                            onDismiss()
                        },
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}
