package see.day.designsystem.util

import see.day.designsystem.R
import see.day.model.record.daily.DailyEmotion

val DailyEmotion.largeIconRes: Int
    get() = when (this) {
        DailyEmotion.Normal -> R.drawable.emotion_normal
        DailyEmotion.Happy -> R.drawable.emotion_happy
        DailyEmotion.Peaceful -> R.drawable.emotion_peaceful
        DailyEmotion.Funny -> R.drawable.emotion_funny
        DailyEmotion.Love -> R.drawable.emotion_love
        DailyEmotion.Tired -> R.drawable.emotion_tired
        DailyEmotion.Panic -> R.drawable.emotion_panic
        DailyEmotion.Sad -> R.drawable.emotion_sad
        DailyEmotion.Angry -> R.drawable.emotion_angry
    }
