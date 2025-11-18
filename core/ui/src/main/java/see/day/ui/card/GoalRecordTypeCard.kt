package see.day.ui.card

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
import see.day.util.goalBody
import see.day.util.goalRecordIcon
import see.day.util.goalTitle

@Composable
internal fun GoalRecordTypeCard(modifier: Modifier = Modifier, recordType: RecordType, isClicked: Boolean, onClickItem: (RecordType) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
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
                painter = painterResource(recordType.goalRecordIcon()),
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
                    text = stringResource(recordType.goalTitle()),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    modifier = modifier.padding(top = 4.dp),
                    text = stringResource(recordType.goalBody()),
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
        GoalRecordTypeCard(
            recordType = RecordType.DAILY,
            isClicked = isChecked,
            onClickItem = onClickItem
        )
    }
}
