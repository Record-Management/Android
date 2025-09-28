package see.day.navigation.daily

import kotlinx.serialization.Serializable
import see.day.designsystem.util.DailyEmotion

@Serializable data object Daily

@Serializable data class DailyWrite(val emotion: DailyEmotion)

@Serializable data class DailyDetail(val id: String)
