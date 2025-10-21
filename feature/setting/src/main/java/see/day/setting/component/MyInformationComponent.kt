package see.day.setting.component

import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray50
import see.day.designsystem.theme.gray60
import see.day.designsystem.theme.gray90
import see.day.model.login.SocialType
import see.day.setting.R

@Composable
internal fun MyInformationComponent(
    modifier: Modifier = Modifier,
    nickname: String,
    birthDate: String,
    socialType: SocialType,
    onNicknameChanged: (String) -> Unit,
    onBirthdayChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier.padding(top = 10.dp)
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
                .padding(16.dp)
        ) {
            NicknameComponent(modifier, nickname, {})
        }
    }
}

@Composable
private fun NicknameComponent(modifier: Modifier, nickname: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
            modifier = Modifier.padding(end = (1).dp)
        )
        Image(
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_right),
            contentDescription = "선택 버튼",
            modifier = Modifier.size(12.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(gray50)
        )
    }
}

@Preview
@Composable
private fun MyInformationComponentPreview() {
    SeeDayTheme {
        MyInformationComponent(
            modifier = Modifier.fillMaxSize(),
            nickname = "네즈코",
            birthDate = "1995/09/23",
            socialType = SocialType.KAKAO,
            onNicknameChanged = {},
            onBirthdayChanged = {}
        )
    }
}
