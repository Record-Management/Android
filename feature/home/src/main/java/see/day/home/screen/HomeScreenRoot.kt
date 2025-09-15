package see.day.home.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.home.component.HomeImage
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import see.day.home.R
import see.day.home.component.HomeTopBar

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier
) {
    HomeScreen(
        modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberStandardBottomSheetState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState)

    val configuration = LocalConfiguration.current
    val bottomSheetPeekHeight = (configuration.screenHeightDp.dp) * 0.6f
    val statusBarPadding = WindowInsets.statusBars
        .asPaddingValues()
    val topPaddingFraction = calculateTopPaddingFraction(configuration, statusBarPadding)

    var minOffset by remember { mutableStateOf<Float?>(null) }
    var maxOffset by remember { mutableStateOf<Float?>(null) }
    var toolbarAlpha by remember { mutableStateOf(0f) }

    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.requireOffset() }
            .collect { offset ->
                // 최초 수집 시 최소/최대 값 기억
                if (minOffset == null || offset < minOffset!!) minOffset = offset
                if (maxOffset == null || offset > maxOffset!!) maxOffset = offset

                val min = minOffset
                val max = maxOffset
                if (min != null && max != null) {
                    // 0f ~ 1f 사이로 정규화해서 알파 계산
                    toolbarAlpha = 1f - ((offset - min) / (max - min)).coerceIn(0f, 1f)
                }
            }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HomeImage(modifier)
        BottomSheetScaffold (
            scaffoldState = bottomSheetScaffoldState,
            topBar = {
                HomeTopBar(
                    modifier = modifier,
                    alpha = toolbarAlpha,
                    isFullExpand = bottomSheetState.currentValue == SheetValue.Expanded
                )
            },
            containerColor = Color.Transparent,
            sheetContent = {
                Column(
                    modifier = modifier.fillMaxHeight(fraction = topPaddingFraction)
                ) {
                    Text("teaseda")
                    if(bottomSheetState.currentValue == SheetValue.Expanded) {
                        Text("hello I am Expended")
                    }
                }
            },
            sheetPeekHeight = bottomSheetPeekHeight,
            sheetShape = if(bottomSheetState.currentValue == SheetValue.Expanded) {
                RoundedCornerShape(0.dp)
            } else {
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            }
        ) { innerPadding ->
        }
        if(bottomSheetState.currentValue != SheetValue.Expanded) {
            FloatingActionButton(
                onClick = {},
                modifier = modifier.align(Alignment.BottomEnd).padding(end = 16.dp, bottom = 20.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.image_edit),
                    contentDescription = "추가하기 버튼",
                    modifier = modifier.size(24.dp)
                )
            }
        }

    }
}

fun calculateTopPaddingFraction(configuration: Configuration, statusBarPaddings: PaddingValues) : Float {
    val screenHeight = configuration.screenHeightDp.dp
    val topBarHeight = 60.dp
    return (screenHeight - statusBarPaddings.calculateTopPadding() - topBarHeight) / screenHeight
}

@Preview
@Composable
private fun HomeScreenPreview() {
    SeeDayTheme {
        HomeScreen()
    }
}
