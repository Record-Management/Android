package see.day.designsystem.effect

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp

fun Modifier.bottomShadow(shadow: Float): Modifier = this.then(Modifier.drawWithContent {
    clipRect (
        left = 0f,
        top = 0f,
        right = size.width,
        bottom = size.height + shadow.dp.toPx()
    ) {
        this@drawWithContent.drawContent()
    }
})
