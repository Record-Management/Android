package see.day.setting.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.model.login.SocialType
import see.day.setting.R
import see.day.setting.component.AlertSettingComponent
import see.day.setting.component.ExtSettingComponent
import see.day.setting.component.MyInformationComponent
import see.day.setting.state.SettingUiEffect
import see.day.setting.state.SettingUiEvent
import see.day.setting.state.SettingUiState
import see.day.setting.viewModel.SettingViewModel
import see.day.ui.topbar.CommonAppBar

@Composable
fun SettingScreenRoot(
    viewModel: SettingViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onGoSettingGoalNotification: () -> Unit,
    onGoSettingRecordNotification: () -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { uiEffect ->
            when (uiEffect) {
                SettingUiEffect.OnPopBack -> {
                    onBack()
                }
                SettingUiEffect.OnGoGoalNotification -> {
                    onGoSettingGoalNotification()
                }
                SettingUiEffect.OnGoRecordNotification -> {
                    onGoSettingRecordNotification()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    SettingScreen(
        uiState = uiState,
        uiEvent = viewModel::onEvent,
    )
}

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    uiState: SettingUiState,
    uiEvent: (SettingUiEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .systemBarsPadding()
            .background(gray20),
        topBar = {
            CommonAppBar(
                modifier = Modifier,
                title = R.string.setting,
                backgroundColor = gray20,
                onClickBackButton = { uiEvent(SettingUiEvent.OnPopBack) }
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
                nickname = uiState.nickname,
                birthDate = uiState.birthDate,
                socialType = SocialType.KAKAO,
                onNicknameChanged = { nickname ->
                    uiEvent(SettingUiEvent.OnChangedNickname(nickname))
                },
                onBirthDateChanged = { birthdate ->
                    uiEvent(SettingUiEvent.OnChangedBirthDate(birthdate))
                },
                onClickDeleteCurrentGoal = {
                    uiEvent(SettingUiEvent.OnClickDeleteCurrentGoal)
                }
            )
            AlertSettingComponent(
                modifier = Modifier.padding(top = 24.dp),
                onClickAppAlert = {
                    uiEvent(SettingUiEvent.OnClickGoalNotification)
                },
                onClickRecordsAlert = {
                    uiEvent(SettingUiEvent.OnClickRecordNotification)
                }
            )
            ExtSettingComponent(
                modifier = Modifier.padding(top = 24.dp),
                onClickInquiry = {},
                onClickLogout = {
                    uiEvent(SettingUiEvent.OnClickLogout)
                },
                onClickWithdrawal = {
                    uiEvent(SettingUiEvent.OnClickWithdrawal)
                }
            )
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SeeDayTheme {
        SettingScreen(
            uiState = SettingUiState.init,
            uiEvent = {}
        )
    }
}
