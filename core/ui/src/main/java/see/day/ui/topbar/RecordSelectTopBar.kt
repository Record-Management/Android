package see.day.ui.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import see.day.model.record.RecordType
import see.day.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordSelectTopBar(modifier: Modifier = Modifier, recordType: RecordType, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(min = 56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    stringResource(recordType.selectTopBarTitle()),
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "닫기 버튼",
                    tint = Color.Black,
                    modifier = modifier
                        .size(24.dp)
                        .clickable { onBack() }
                )
            }
        }
    )
}


internal fun RecordType.selectTopBarTitle() : Int{
    return when(this) {
        RecordType.DAILY -> R.string.daily_select_title
        RecordType.EXERCISE -> R.string.exercise_select_title
        RecordType.HABIT -> R.string.habit_select_title
//        RecordType.SCHEDULE -> R.string.schedule_select_title
    }
}
