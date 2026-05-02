package see.day.schedule.component

import androidx.compose.foundation.Image
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
import see.day.schedule.R

@Composable
internal fun ScheduleTopBar(
    modifier: Modifier = Modifier,
    onClickCloseButton: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.schedule_top_bar_title),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge,
        )
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
                .clickable { onClickCloseButton() },
            painter = painterResource(see.day.ui.R.drawable.ic_close),
            contentDescription = "뒤로가기 버튼"
        )
    }
}

@Preview
@Composable
private fun ScheduleTopBarPreview() {
    SeeDayTheme {
        ScheduleTopBar(onClickCloseButton = {})
    }
}
