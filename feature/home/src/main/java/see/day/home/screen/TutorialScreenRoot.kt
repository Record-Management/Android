package see.day.home.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.home.R
import see.day.home.component.HomeTopBar
import see.day.home.state.tutorial.TutorialUiEffect
import see.day.home.state.tutorial.TutorialUiEvent
import see.day.home.state.tutorial.TutorialUiState
import see.day.home.viewModel.TutorialViewModel

@Composable
fun TutorialScreenRoot(
    viewModel: TutorialViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    BackHandler(true) {
        if (uiState is TutorialUiState.Tutorial) {
            viewModel.onAction(TutorialUiEvent.OnClickCloseButton)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is TutorialUiEffect.NavigateToHome -> {
                    onBack()
                }
            }

        }
    }
    TutorialScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun TutorialScreen(
    modifier: Modifier = Modifier,
    uiState: TutorialUiState,
    onAction: (TutorialUiEvent) -> Unit
) {
    when (uiState) {
        is TutorialUiState.Tutorial -> {
            TutorialPage(
                onClickBackButton = { onAction(TutorialUiEvent.OnClickCloseButton) }
            )
        }

        is TutorialUiState.Loading -> {
            TutorialLoader()
        }
    }
}

@Composable
private fun TutorialPage(
    modifier: Modifier = Modifier,
    onClickBackButton: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color(0xBF111111))
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.tutorial),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
        Image(
            painter = painterResource(R.drawable.coach_mark_close),
            modifier = Modifier
                .padding(top = 40.dp, end = 16.dp)
                .align(Alignment.TopEnd)
                .clickable { onClickBackButton() },
            contentScale = ContentScale.Crop,
            contentDescription = "뒤로가기 버튼"
        )
        Image(
            painter = painterResource(R.drawable.coach_mark_floating),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(end = 16.dp, bottom = 30.dp)
                .align(Alignment.BottomEnd),
            contentDescription = "플로팅 버튼",
            alignment = Alignment.BottomEnd
        )
    }
}

@Composable
private fun TutorialLoader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        HomeTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            alpha = 0f,
            mainRecordType = null,
            goalDays = null,
            isFullExpand = false,
            onClickBackButton = {},
            onClickNotification = {},
            onClickSetting = {}
        )

        Image(
            painter = painterResource(R.drawable.tutorial_loader),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 256.dp)
        )
    }
}

@Preview
@Composable
private fun TutorialScreenPreview() {
    var uiState by remember { mutableStateOf<TutorialUiState>(TutorialUiState.Tutorial) }
    SeeDayTheme {
        TutorialScreen(
            uiState = uiState,
            onAction = {
                uiState = TutorialUiState.Loading
            }
        )
    }
}
