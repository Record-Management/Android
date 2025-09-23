package see.day.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeTopBar(modifier: Modifier = Modifier, alpha: Float, isFullExpand: Boolean, onClickBackButton: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = "알람 창",
                    modifier = modifier
                        .padding(end = 9.dp)
                        .clickable { },
                    tint = gray100
                )
                Icon(
                    painter = painterResource(R.drawable.ic_setting),
                    contentDescription = "설정 창",
                    modifier = modifier
                        .padding(end = 16.dp)
                        .clickable { },
                    tint = gray100
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = if(isFullExpand) Color.White.copy(alpha = 1.0f) else Color.White.copy(alpha = alpha)
        ),
        navigationIcon = {
            if (isFullExpand) {
                IconButton(
                    onClick = {
                        onClickBackButton()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "뒤로 가기 버튼",
                        tint = gray100
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    )
}

@Preview
@Composable
private fun HomeTopBarPreview() {
    SeeDayTheme {
        HomeTopBar(
            alpha = 0f,
            isFullExpand = false,
            onClickBackButton = {}
        )
    }
}
