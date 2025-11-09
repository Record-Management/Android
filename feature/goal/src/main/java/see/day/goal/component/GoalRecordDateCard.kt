package see.day.goal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray40
import see.day.designsystem.theme.gray70
import see.day.goal.R
import see.day.model.record.RecordType

@Composable
internal fun GoalRecordDateCard(
    modifier: Modifier = Modifier,
    recordType: RecordType,
    startDate: String,
    endDate: String
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(gray20)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .height(49.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.my_goal_title),
                style = MaterialTheme.typography.headlineLarge.copy(color = gray70)
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = recordType.title,
                style = MaterialTheme.typography.titleSmall
            )
        }
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .width(1.dp)
            .fillMaxHeight()
            .background(gray40))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.goal_during),
                style = MaterialTheme.typography.headlineLarge.copy(color = gray70)
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = startDate.replace("-",".").substring(2) + " ~ " + endDate.replace("-",".").substring(2),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview
@Composable
private fun GoalRecordDateCardPreview() {
    SeeDayTheme {
        GoalRecordDateCard(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 26.dp),
            recordType = RecordType.HABIT,
            startDate = "2025-10-23",
            endDate = "2025-11-02"
        )
    }
}
