package see.day.model.record.daily

import see.day.model.time.DateTime
import see.day.model.time.DateTimeFormatter

data class DailyRecordDetail(
    val content: String,
    val emotion: String,
    val recordDate: DateTimeFormatter,
    val imageUrls : List<String>
)
