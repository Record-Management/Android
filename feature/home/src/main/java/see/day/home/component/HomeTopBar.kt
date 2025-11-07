package see.day.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import see.day.model.record.RecordType
import see.day.util.getIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeTopBar(modifier: Modifier = Modifier, alpha: Float, mainRecordType: RecordType, goalDays: Int, isFullExpand: Boolean, onClickBackButton: () -> Unit, onClickSetting: () -> Unit, onClickNotification: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = modifier.fillMaxWidth(),
            ) {
                val goalDaysAlignment = if (isFullExpand) {
                    Alignment.Center
                } else {
                    Alignment.CenterStart
                }

                GoalDays(
                    modifier = Modifier
                        .align(goalDaysAlignment)
                        .offset(x = if (isFullExpand) (-40).dp else (-16).dp),
                    recordType = mainRecordType,
                    goalDays = goalDays
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_notification),
                        contentDescription = "알람 창",
                        modifier = modifier
                            .padding(end = 9.dp)
                            .clickable { onClickNotification() },
                        tint = gray100
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_setting),
                        contentDescription = "설정 창",
                        modifier = modifier
                            .padding(end = 16.dp)
                            .clickable { onClickSetting() },
                        tint = gray100
                    )
                }

            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = if (isFullExpand) Color.White.copy(alpha = 1.0f) else Color.White.copy(alpha = alpha)
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
                        tint = gray100,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    )
}

@Composable
private fun GoalDays(
    modifier: Modifier = Modifier,
    recordType: RecordType,
    goalDays: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(recordType.getIcon()),
            contentDescription = recordType.title,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "D-$goalDays",
            style = MaterialTheme.typography.displayMedium.copy()
        )
    }
}

@Preview
@Composable
private fun HomeTopBarPreview() {
    SeeDayTheme {
        HomeTopBar(
            alpha = 0f,
            mainRecordType = RecordType.EXERCISE,
            goalDays = 10,
            isFullExpand = false,
            onClickBackButton = {},
            onClickSetting = {},
            onClickNotification = {}
        )
    }
}

@Preview
@Composable
private fun GoalDaysPreview() {
    SeeDayTheme {
        GoalDays(
            modifier = Modifier.fillMaxWidth(),
            recordType = RecordType.EXERCISE,
            goalDays = 10
        )
    }
}
