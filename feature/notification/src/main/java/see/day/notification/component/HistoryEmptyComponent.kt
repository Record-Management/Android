package see.day.notification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import see.day.designsystem.theme.gray50
import see.day.notification.R

@Composable
internal fun HistoryEmptyComponent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        Column(
            modifier = Modifier.weight(0.7f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_history_empty),
                contentDescription = "알림 히스토리 없음",
                modifier = Modifier.size(40.dp)
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = stringResource(R.string.empty_history_description),
                style = MaterialTheme.typography.labelSmall.copy(gray50)
            )
        }
    }
}

@Preview
@Composable
private fun HistoryEmptyComponentPreview() {
    SeeDayTheme {
        HistoryEmptyComponent(
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
