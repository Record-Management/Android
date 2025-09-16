package see.day.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
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
    val builder = rememberBalloonBuilder {
        setArrowSize(19)
        setArrowPosition(0.8f)
        setMarginRight(12)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(8)
        setCornerRadius(100f)
        setElevation(0)
        setBackgroundColor(gray20)
    }

    var balloonWindow: BalloonWindow? by remember { mutableStateOf(null) }


    Balloon(
        builder = builder,
        onBalloonWindowInitialized = { balloonWindow = it },
        balloonContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                RecordFilterType.entries.forEach { type ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = if (selectedFilterType == type) {
                            modifier
                                .size(30.dp)
                                .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable {
                                    balloonWindow?.dismiss()
                                }
                        } else {
                            modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable {
                                    onClickFilterType(type)
                                    balloonWindow?.dismiss()
                                }
                        }
                    ) {
                        Image(
                            painter = painterResource(type.iconRes),
                            contentDescription = "type $type",
                            modifier = modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    ) { _ ->
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(100.dp))
                .background(gray20)
                .padding(4.dp)
                .clickable { balloonWindow?.showAlignBottom() },
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
