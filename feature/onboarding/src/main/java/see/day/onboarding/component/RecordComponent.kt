package see.day.onboarding.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.model.record.RecordType
import see.day.onboarding.R
import see.day.onboarding.util.body
import see.day.onboarding.util.getIcon
import see.day.onboarding.util.title

@Composable
internal fun RecordComponent(modifier: Modifier = Modifier, recordType: RecordType, isClicked: Boolean, onClickItem: (RecordType) -> Unit) {
    Surface(
        modifier = Modifier
            .padding(top = 16.dp)
            .height(82.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClickItem(recordType) },
        border = if (isClicked) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        } else {
            BorderStroke(1.dp, gray20)
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(recordType.getIcon()),
                contentDescription = "$recordType Image",
                modifier = modifier.size(50.dp)
            )
            Column(
                modifier = modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(recordType.title()),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    modifier = modifier.padding(top = 4.dp),
                    text = stringResource(recordType.body()),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = modifier.weight(1f))
            Image(
                painter = painterResource(
                    if (isClicked) see.day.designsystem.R.drawable.ic_checked else see.day.designsystem.R.drawable.ic_unchecked
                ),
                contentDescription = if (isClicked) "checked" else "unChecked"
            )
        }
    }
}

@Preview
@Composable
private fun RecordComponentPreview() {
    val recordType = RecordType.DAILY
    var isChecked by remember { mutableStateOf(false) }
    val onClickItem: (RecordType) -> Unit = { type ->
        if (recordType == type) {
            isChecked = !isChecked
        }
    }
    SeeDayTheme {
        RecordComponent(
            recordType = RecordType.DAILY,
            isClicked = isChecked,
            onClickItem = onClickItem
        )
    }
}
