package see.day.main.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreenRoot(modifier: Modifier = Modifier, viewModel: SettingViewModel = hiltViewModel()) {
    SettingScreen(
        onClickLogout = viewModel::logout
    )
}

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    onClickLogout: () -> Unit
) {
    Column(
        modifier = modifier.systemBarsPadding()
    ) {
        Text("세팅화면")
        Button(
            onClickLogout
        ) {
            Text("로그아웃 하기")
        }
    }
}
