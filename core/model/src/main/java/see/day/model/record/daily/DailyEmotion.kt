package see.day.model.record.daily

import kotlinx.serialization.Serializable

@Serializable
enum class DailyEmotion {
    Normal,
    Happy,
    Peaceful,
    Funny,
    Love,
    Tired,
    Panic,
    Sad,
    Angry
}
