package see.day.main.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingScreenRoot(modifier: Modifier = Modifier) {
    SettingScreen()
}

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.systemBarsPadding()
    ) {
        Text("세팅화면")
    }
}
