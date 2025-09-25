package see.day.model.record.daily

data class RegisteredDailyRecord(
    val id: String,
    val type: String,
    val emotion: String,
    val content: String,
    val imageUrls: String,
    val recordDate: String,
    val recordTime: String,
    val createdAt: String,
    val updatedAt: String
)
