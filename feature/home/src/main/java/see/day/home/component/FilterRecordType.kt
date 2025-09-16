package see.day.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.home.R
import see.day.home.util.RecordFilterType

@Composable
internal fun SelectedFilterRecordType(
    modifier: Modifier = Modifier,
    selectedFilterType: RecordFilterType,
    onClickFilterType: (RecordFilterType) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(gray20)
            .padding(4.dp)
            .clickable { onClickFilterType(RecordFilterType.ALL) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(selectedFilterType.iconRes),
                contentDescription = "기록 타입 필터",
                modifier = modifier.size(24.dp)
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_arrow_down),
            contentDescription = "아래꺽쇠 아이콘",
            modifier = modifier
                .size(20.dp)
                .padding(start = 2.dp)
        )
    }
}

@Preview
@Composable
private fun SelectedFilterRecordTypePreview() {
    SeeDayTheme {
        SelectedFilterRecordType(
            selectedFilterType = RecordFilterType.DAILY,
            onClickFilterType = {}
        )
    }
}
