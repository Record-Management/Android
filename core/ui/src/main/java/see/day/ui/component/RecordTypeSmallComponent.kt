package see.day.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.effect.bottomShadow
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.model.record.RecordType
import see.day.util.getBigIcon

@Composable
fun RecordTypeSmallComponent(modifier: Modifier = Modifier, currentRecordType: RecordType, selectedRecordType: RecordType?, onClickRecordType: (RecordType) -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color = if (currentRecordType == selectedRecordType) MaterialTheme.colorScheme.primary else gray20),
        shadowElevation = if (currentRecordType == selectedRecordType) 2.dp else 0.dp,
        modifier = if (currentRecordType == selectedRecordType) modifier.bottomShadow(2f).padding(vertical = 8.dp) else modifier.padding(vertical = 8.dp)
    ) {
        Row(
            modifier = modifier
                .heightIn(min = 82.dp)
                .fillMaxWidth()
                .clickable { onClickRecordType(currentRecordType) }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(currentRecordType.getBigIcon()),
                contentDescription = currentRecordType.title,
                contentScale = ContentScale.FillBounds,
                modifier = modifier.size(50.dp)
            )
            Text(
                text = currentRecordType.title,
                modifier = modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = modifier.weight(1f))
            Image(
                painter = painterResource(
                    if (currentRecordType == selectedRecordType) see.day.designsystem.R.drawable.ic_checked else see.day.designsystem.R.drawable.ic_unchecked
                ),
                contentDescription = "checked"
            )
        }
    }
}

@Preview
@Composable
private fun RecordTypeSmallComponentPreview() {
    SeeDayTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RecordTypeSmallComponent(
                currentRecordType = RecordType.EXERCISE,
                selectedRecordType = RecordType.EXERCISE,
                onClickRecordType = {}
            )
            RecordTypeSmallComponent(
                currentRecordType = RecordType.HABIT,
                selectedRecordType = RecordType.EXERCISE,
                onClickRecordType = {}
            )
        }
    }
}
