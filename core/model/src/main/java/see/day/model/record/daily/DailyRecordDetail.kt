package see.day.model.record.daily

data class DailyRecordDetail(
    val id: String,
    val type: String,
    val emotion: DailyEmotion,
    val content: String,
    val imageUrls: List<String>,
    val recordDate: String,
    val recordTime: String,
    val createdAt: String,
    val updatedAt: String
)
