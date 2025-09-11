package see.day.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.home.R
import see.day.home.component.HomeImage
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
    var bottomSheetExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HomeImage(modifier)
        Scaffold(
            modifier = modifier.wrapContentHeight(),
            topBar = {
                HomeTopBar(
                    modifier = modifier,
                    isFullExpand = bottomSheetExpanded
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Text("asdsad",modifier = modifier
                .padding(innerPadding)
                .background(gray100))
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    SeeDayTheme {
        HomeScreen()
    }
}
