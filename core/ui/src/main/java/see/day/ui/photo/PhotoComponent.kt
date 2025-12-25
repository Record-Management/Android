package see.day.ui.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import see.day.designsystem.theme.SeeDayTheme
import see.day.ui.R

@Composable
fun PhotoComponent(modifier: Modifier = Modifier, uri: String, isRemovable: Boolean = false, onClickDeleteButton: (String) -> Unit = {}) {
    Box(
        modifier = modifier.size(if (isRemovable) 106.dp else 100.dp).clip(RoundedCornerShape(8.dp)).clipToBounds()
    ) {
        AsyncImage(
            model = uri,
            contentDescription = "",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.BottomStart),
            contentScale = ContentScale.Crop
        )

        if (isRemovable) {
            Image(
                painter = painterResource(id = R.drawable.ic_delete_photo),
                contentDescription = "닫기",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 2.dp, y = (-2).dp)
                    .size(20.dp)
                    .clickable { onClickDeleteButton(uri) }
                    .graphicsLayer {
                        shadowElevation = 3.dp.toPx()
                        translationX = 1.dp.toPx()  // 오른쪽으로
                        translationY = 1.dp.toPx()  // 아래로
                        shape = CircleShape
                        clip = false
                        ambientShadowColor = Color.Black.copy(alpha = 0.1f)
                        spotShadowColor = Color.Black.copy(alpha = 0.15f)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun PhotoComponentPreview() {
    SeeDayTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PhotoComponent(
                uri = "https://wikidocs.net/images/page/49159/png-2702691_1920_back.png",
                isRemovable = true,
                onClickDeleteButton = {}
            )
        }
    }
}
