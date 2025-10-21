package see.day.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.setting.component.SettingTopBar
import see.day.setting.viewModel.SettingViewModel

@Composable
fun SettingScreenRoot(viewModel: SettingViewModel = hiltViewModel(), onBack: () -> Unit) {
    SettingScreen(
        onClickLogout = viewModel::logout,
        onClickDelete = viewModel::deleteUser,
        onClickBackButton = onBack,
    )
}

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    onClickLogout: () -> Unit,
    onClickDelete: () -> Unit,
    onClickBackButton: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .systemBarsPadding()
            .background(gray20),
        topBar = {
            SettingTopBar(
                modifier = Modifier,
                onClickBackButton = onClickBackButton
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(gray20)
        ) {
            Text("세팅화면")
            Button(
                onClickLogout
            ) {
                Text("로그아웃 하기")
            }
            Button(
                onClickDelete
            ) {
                Text("회원 탈퇴 하기")
            }
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SeeDayTheme {
        SettingScreen(
            onClickDelete = {},
            onClickLogout = {},
            onClickBackButton = {}
        )
    }
}
