package see.day.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.model.calendar.DailyRecordDetails
import see.day.model.calendar.DailyRecordDetail
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion
import see.day.ui.component.record.overview.DailyRecordOverview
import see.day.ui.component.record.overview.ExerciseRecordOverview

@Composable
fun CalendarDetail(
    modifier: Modifier = Modifier,
    dailyRecordDetails: DailyRecordDetails,
    onClickOverview: (RecordType, String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = dailyRecordDetails.formatFullDate,
            style = MaterialTheme.typography.titleLarge,
            color = gray100
        )
        Column(
            modifier = modifier.padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            dailyRecordDetails.records.forEach { record ->
                when(record) {
                    is DailyRecordDetail -> {
                        DailyRecordOverview(
                            recordId = record.id,
                            dailyEmotion = record.emotion,
                            recordDate = record.fullRecordTime,
                            content = record.content,
                            photoUrls = record.imageUrls,
                            onClickItem = onClickOverview
                        )
                    }
                    is ExerciseRecordDetail -> {
                        ExerciseRecordOverview(
                            exerciseRecord = record,
                            onClickItem = onClickOverview
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun CalendarDetailPreview() {
    SeeDayTheme {
        CalendarDetail(
            dailyRecordDetails = DailyRecordDetails("2025-09-12", listOf(DailyRecordDetail(id = "", type = RecordType.DAILY,emotion =  DailyEmotion.Love,content = "asdasdasd", imageUrls =  listOf("https://wikidocs.net/images/page/49159/png-2702691_1920_back.png"), recordTime =  "", recordDate = "13:30", createdAt =  "", updatedAt =  "")))
        ) { recordType, recordId -> }
    }
}
