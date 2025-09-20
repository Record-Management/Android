package see.day.designsystem.util

import kotlinx.serialization.Serializable
import see.day.designsystem.R

@Serializable
enum class DailyEmotion {
    SHY,
    WINK,
    NORMAL,
    SUNGLASSES,
    LOVE,
    FRUSTRATION,
    HUM,
    SAD,
    ANGRY
}

// DrawbleRes는 런타임에 결정되므로 직렬화가 불가능
val DailyEmotion.largeIconRes: Int
    get() = when (this) {
        DailyEmotion.SHY -> R.drawable.emotion_shy_large
        DailyEmotion.WINK -> R.drawable.emotion_wink_large
        DailyEmotion.NORMAL -> R.drawable.emotion_normal_large
        DailyEmotion.SUNGLASSES -> R.drawable.emotion_sunglasses_large
        DailyEmotion.LOVE -> R.drawable.emotion_love_large
        DailyEmotion.FRUSTRATION -> R.drawable.emotion_frustration_large
        DailyEmotion.HUM -> R.drawable.emotion_hum_large
        DailyEmotion.SAD -> R.drawable.emotion_sad_large
        DailyEmotion.ANGRY -> R.drawable.emotion_angry_large
    }
