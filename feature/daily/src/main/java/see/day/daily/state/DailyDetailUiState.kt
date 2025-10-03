package see.day.daily.state

import see.day.model.record.daily.DailyRecordInput
import see.day.model.record.daily.DailyEmotion
import see.day.model.time.DateTime
import see.day.model.time.DateTimeFormatter
import see.day.model.time.formatter.KoreanDateTimeFormatter

data class DailyDetailUiState(
    val emotion: DailyEmotion,
    val text: String,
    val dateTime: DateTimeFormatter,
    val photos: List<String>,
    val editMode: EditMode
) {
    sealed class EditMode {
        data object Create : EditMode()
        data class Edit(val originalRecord: DailyRecordInput, val recordId: String) : EditMode()
    }

    val canSubmit: Boolean = when (editMode) {
        is EditMode.Create -> text.isNotEmpty()
        is EditMode.Edit -> {
            val original = editMode.originalRecord
            (
                emotion != original.emotion ||
                    text != original.content ||
                    photos != original.imageUrls
                ) && text.isNotEmpty()
        }
    }

    companion object {
        val init = DailyDetailUiState(
            emotion = DailyEmotion.Love,
            text = "",
            dateTime = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)),
            photos = listOf(),
            editMode = EditMode.Create
        )
    }
}
