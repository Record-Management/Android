package see.day.daily.util

import see.day.designsystem.util.DailyEmotion

sealed interface DailyRecordPostType {
    data class EditDailyRecordPost(val id: String) : DailyRecordPostType
    data class WriteDailyRecordPost(val emotion: DailyEmotion) : DailyRecordPostType
}
