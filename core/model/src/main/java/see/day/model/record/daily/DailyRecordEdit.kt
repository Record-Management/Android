package see.day.model.record.daily

data class DailyRecordEdit(
    val recordId: String,
    val content: String,
    val emotion: DailyEmotion,
    val imageUrls: List<String>
)
