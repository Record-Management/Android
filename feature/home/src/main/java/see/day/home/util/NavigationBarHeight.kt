package see.day.home.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("InternalInsetResource")
@Composable
fun rememberNavigationBarHeight(): Dp {
    val context = LocalContext.current
    val density = LocalDensity.current

    return remember {
        val resources = context.resources
        val resourceId = resources.getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )

        if (resourceId > 0) {
            with(density) {
                resources.getDimensionPixelSize(resourceId).toDp()
            }
        } else {
            48.dp // 기본값
        }
    }
}
