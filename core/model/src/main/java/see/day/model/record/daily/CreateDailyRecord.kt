package see.day.model.record.daily

import see.day.model.time.DateTimeFormatter

data class CreateDailyRecord(
    val content: String,
    val emotion: DailyEmotion,
    val recordDate: DateTimeFormatter,
    val imageUrls : List<String>
)
