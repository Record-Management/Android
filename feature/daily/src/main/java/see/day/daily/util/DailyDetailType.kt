package see.day.daily.util

import see.day.designsystem.util.DailyEmotion

sealed interface DailyDetailType {
    data class EditDailyDetail(val id: String) : DailyDetailType
    data class WriteDailyDetail(val emotion: DailyEmotion) : DailyDetailType
}
