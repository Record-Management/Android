package see.day.ui.card

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.ui.R

@Composable
fun ActionBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes title: Int,
    @StringRes body: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.labelMedium.copy(Color.White)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(Color.White)
                .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(body),
                style = MaterialTheme.typography.displaySmall.copy(color = MaterialTheme.colorScheme.primary)
            )
            Image(
                painter = painterResource(R.drawable.ic_arrow_right),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                contentDescription = "선택",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ActionBannerPreview() {
    SeeDayTheme {
        ActionBanner(
            modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp),
            onClick = {},
            title = R.string.test_title,
            body = R.string.test_body
        )
    }
}
