package see.day.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray100
import see.day.model.calendar.DailyRecordDetails
import see.day.model.calendar.DailyRecordDetail
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.calendar.HabitRecordDetail
import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion
import see.day.ui.component.record.overview.DailyRecordOverview
import see.day.ui.component.record.overview.ExerciseRecordOverview
import see.day.ui.component.record.overview.HabitRecordOverView
import see.day.ui.dialog.DialogBackground

@Composable
fun CalendarDetail(
    modifier: Modifier = Modifier,
    dailyRecordDetails: DailyRecordDetails,
    onClickRevise: (RecordType, String) -> Unit,
    onClickDelete: (RecordType, String) -> Unit,
    onClickUpdateHabitRecordIsCompleted: (String, Boolean, String) -> Unit
) {
    var openLongPressureDialog by remember { mutableStateOf(Triple(false, RecordType.DAILY, "")) }
    if (openLongPressureDialog.first) {
        LongPressureDialog(
            onDismiss = { openLongPressureDialog = openLongPressureDialog.copy(first = false) },
            recordType = openLongPressureDialog.second,
            recordId = openLongPressureDialog.third,
            onClickRevise = onClickRevise,
            onClickRemove = onClickDelete
        )
    }

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
            modifier = Modifier.padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            dailyRecordDetails.records.forEach { record ->
                when (record) {
                    is DailyRecordDetail -> {
                        DailyRecordOverview(
                            recordId = record.id,
                            dailyEmotion = record.emotion,
                            recordDate = record.fullRecordTime,
                            content = record.content,
                            photoUrls = record.imageUrls,
                            onClickItem = onClickRevise,
                            onClickLongItem = { openLongPressureDialog = openLongPressureDialog.copy(true, record.type, record.id) }
                        )
                    }

                    is ExerciseRecordDetail -> {
                        ExerciseRecordOverview(
                            exerciseRecord = record,
                            onClickItem = onClickRevise,
                            onClickLongItem = { openLongPressureDialog = openLongPressureDialog.copy(true, record.type, record.id) }
                        )
                    }

                    is HabitRecordDetail -> {
                        HabitRecordOverView(
                            habitRecord = record,
                            onClickItem = onClickRevise,
                            onClickLongItem = { openLongPressureDialog = openLongPressureDialog.copy(true, record.type, record.id) },
                            onClickChecked = onClickUpdateHabitRecordIsCompleted
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun LongPressureDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    recordType: RecordType,
    recordId: String,
    onClickRevise: (RecordType, String) -> Unit,
    onClickRemove: (RecordType, String) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        DialogBackground(
            modifier = modifier,
            onDismiss = { onDismiss() },
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 33.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .clickable(onClick = {}, indication = null, interactionSource = remember { MutableInteractionSource() })
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClickRevise(recordType, recordId)
                                onDismiss()
                            }
                            .padding(16.dp),
                        text = "수정하기",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClickRemove(recordType, recordId)
                                onDismiss()
                            }
                            .padding(16.dp),
                        text = "삭제하기",
                        style = MaterialTheme.typography.displayLarge
                    )
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
            dailyRecordDetails = DailyRecordDetails(
                "2025-09-12",
                listOf(DailyRecordDetail(id = "", type = RecordType.DAILY, emotion = DailyEmotion.Love, content = "asdasdasd", imageUrls = listOf("https://wikidocs.net/images/page/49159/png-2702691_1920_back.png"), recordTime = "13:30", recordDate = "2025-11-10", createdAt = "", updatedAt = ""))
            ),
            onClickRevise = { recordType, recordId -> },
            onClickDelete = { recordType, recordId -> },
            onClickUpdateHabitRecordIsCompleted = { recordId, isCompleted, recordDate -> }
        )
    }
}
