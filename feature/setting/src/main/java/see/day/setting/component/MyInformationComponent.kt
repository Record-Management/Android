package see.day.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray60
import see.day.designsystem.theme.gray90
import see.day.designsystem.util.getIconRes
import see.day.model.login.SocialType
import see.day.setting.R

@Composable
internal fun MyInformationComponent(
    modifier: Modifier = Modifier,
    nickname: String,
    birthDate: String,
    socialType: SocialType,
    onNicknameChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.my_information),
            style = MaterialTheme.typography.titleSmall
        )
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NicknameComponent(Modifier, nickname, onNicknameChanged)
            BirthdayComponent(Modifier, birthDate, {})
            SocialTypeComponent(Modifier, socialType)
        }
    }
}



@Composable
private fun NicknameComponent(modifier: Modifier, nickname: String, onNicknameChanged: (String) -> Unit) {
    var openNicknameChangeBottomSheet by remember { mutableStateOf(false) }

    if(openNicknameChangeBottomSheet) {
        NicknameChangedBottomSheet(
            modifier = Modifier.padding(horizontal = 16.dp),
            nickname = nickname,
            onDismiss = { openNicknameChangeBottomSheet = false},
            onNicknameChanged = { newNickname ->
                onNicknameChanged(newNickname)
                openNicknameChangeBottomSheet = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openNicknameChangeBottomSheet = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.nickname),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = nickname,
            style = MaterialTheme.typography.labelSmall.copy(color = gray60),
            modifier = Modifier.padding(end = 1.dp)
        )
        Image(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "닉네임 선택 버튼",
            modifier = Modifier.size(12.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(gray50)
        )
    }
}

@Composable
private fun BirthdayComponent(modifier: Modifier, birthDate: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.birthday),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = birthDate,
            style = MaterialTheme.typography.labelSmall.copy(color = gray60),
            modifier = Modifier.padding(end = 1.dp)
        )
        Image(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "생일 선택 버튼",
            modifier = Modifier.size(12.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(gray50)
        )
    }
}

@Composable
private fun SocialTypeComponent(
    modifier: Modifier,
    socialType: SocialType
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.socal_account),
            style = MaterialTheme.typography.labelMedium.copy(color = gray90)
        )
        Spacer(modifier = modifier.weight(1f))
        Image(
            painter = painterResource(socialType.getIconRes()),
            contentDescription = "소셜 계정 ${socialType.name}",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview
@Composable
private fun MyInformationComponentPreview() {
    SeeDayTheme {
        MyInformationComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            nickname = "네즈코",
            birthDate = "1995/09/23",
            socialType = SocialType.KAKAO,
            onNicknameChanged = {},
            onBirthDateChanged = {}
        )
    }
}
