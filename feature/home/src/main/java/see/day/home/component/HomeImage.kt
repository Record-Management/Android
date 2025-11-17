package see.day.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import see.day.designsystem.theme.SeeDayTheme
import see.day.home.R
import see.day.home.util.completeImage
import see.day.model.goal.TreeStage

@Composable
internal fun BoxScope.HomeImage(modifier: Modifier = Modifier, treeStage: TreeStage?) {
    Image(
        painter = painterResource(R.drawable.home_screen_background),
        contentDescription = "홈 화면 이미지",
        modifier = modifier
            .align(Alignment.Center)
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f).align(Alignment.TopCenter),
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = painterResource(treeStage.completeImage()),
            contentDescription = treeStage?.name ?: "목표 미설정",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview
@Composable
private fun HomeImagePreview() {
    SeeDayTheme {
        Box {
            HomeImage(
                treeStage = TreeStage.STAGE_1
            )
        }
    }
}
