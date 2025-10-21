package see.day.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.setting.R

@Composable
internal fun SettingTopBar(
    modifier: Modifier = Modifier,
    onClickBackButton: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(gray20)
            .padding(vertical = 16.dp, horizontal = 14.dp)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
                .clickable { onClickBackButton() },
            painter = painterResource(see.day.ui.R.drawable.ic_arrow_left),
            contentDescription = "뒤로가기 버튼"
        )
        Text(
            text = stringResource(R.string.setting),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
private fun SettingTopBarPreview() {
    SeeDayTheme {
        SettingTopBar {  }
    }
}
