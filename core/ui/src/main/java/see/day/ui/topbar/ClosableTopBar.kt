package see.day.ui.topbar

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClosableTopBar(
    modifier: Modifier,
    onClose: () -> Unit,
    @StringRes title: Int
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "닫기 버튼",
                    modifier = Modifier.size(24.dp).clickable { onClose() }.align(Alignment.CenterEnd)
                )
            }
        }
    )
}

@Preview
@Composable
private fun ClosableTopBarPreview() {
    SeeDayTheme {
        ClosableTopBar(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClose = {},
            title = R.string.test_title
        )
    }
}
