package see.day.navigation.daily

import kotlinx.serialization.Serializable
import see.day.model.record.daily.DailyEmotion

sealed interface DailyRoute {

    @Serializable data object Daily : DailyRoute

    @Serializable data class DailyWrite(val emotion: DailyEmotion) : DailyRoute

    @Serializable data class DailyDetail(val id: String) : DailyRoute

}
