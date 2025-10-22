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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.model.login.SocialType
import see.day.setting.component.AlertSettingComponent
import see.day.setting.component.ExtSettingComponent
import see.day.setting.component.MyInformationComponent
import see.day.setting.component.SettingTopBar
import see.day.setting.viewModel.SettingViewModel

@Composable
fun SettingScreenRoot(viewModel: SettingViewModel = hiltViewModel(), onBack: () -> Unit) {
    SettingScreen(
        onClickBackButton = onBack,
    )
}

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
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
                .padding(horizontal = 16.dp)
        ) {
            MyInformationComponent(
                modifier = Modifier.padding(top = 10.dp),
                nickname = "네즈코",
                birthDate = "2000/01/16",
                socialType = SocialType.KAKAO,
                onNicknameChanged = {},
                onBirthdayChanged = {}
            )
            AlertSettingComponent(
                modifier = Modifier.padding(top = 24.dp),
                onClickAppAlert = {},
                onClickRecordsAlert = {}
            )
            ExtSettingComponent(
                modifier = Modifier.padding(top = 24.dp),
                onClickPolicy = {},
                onClickInquiry = {},
                onClickLogout = {},
                onClickWithdrawal = {}
            )
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SeeDayTheme {
        SettingScreen(
            onClickBackButton = {}
        )
    }
}
