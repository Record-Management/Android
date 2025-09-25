package see.day.designsystem.util

import kotlinx.serialization.Serializable
import see.day.designsystem.R

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

// DrawbleRes는 런타임에 결정되므로 직렬화가 불가능
val DailyEmotion.largeIconRes: Int
    get() = when (this) {
        DailyEmotion.Normal -> R.drawable.emotion_normal_large
        DailyEmotion.Happy -> R.drawable.emotion_happy_large
        DailyEmotion.Peaceful -> R.drawable.emotion_peaceful_large
        DailyEmotion.Funny -> R.drawable.emotion_funny_large
        DailyEmotion.Love -> R.drawable.emotion_love_large
        DailyEmotion.Tired -> R.drawable.emotion_tired_large
        DailyEmotion.Panic -> R.drawable.emotion_panic_large
        DailyEmotion.Sad -> R.drawable.emotion_sad_large
        DailyEmotion.Angry  -> R.drawable.emotion_angry_large
    }
