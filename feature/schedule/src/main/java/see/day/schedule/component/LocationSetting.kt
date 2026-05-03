package see.day.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.gray50
import see.day.schedule.R

@Composable
internal fun LocationSetting(
    modifier: Modifier = Modifier,
    locationText: String,
    onLocationChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_compass),
            contentDescription = "위치 설정",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = stringResource(id = R.string.location),
            style = MaterialTheme.typography.titleSmall,
        )
        BasicTextField(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(height = 52.dp)
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 14.dp),
            value = locationText,
            textStyle = MaterialTheme.typography.displayMedium,
            maxLines = 1,
            onValueChange = onLocationChange,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (locationText.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.location_hint),
                            style = MaterialTheme.typography.displayMedium,
                            color = gray50
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
