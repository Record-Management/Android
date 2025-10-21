package see.day.onboarding.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import see.day.model.record.RecordType
import see.day.onboarding.R

@DrawableRes
fun RecordType.getIcon(): Int {
    return when (this) {
        RecordType.DAILY -> see.day.designsystem.R.drawable.record_daily
        RecordType.EXERCISE -> see.day.designsystem.R.drawable.record_exercise
//        RecordType.SCHEDULE -> see.day.designsystem.R.drawable.record_schedule
        RecordType.HABIT -> see.day.designsystem.R.drawable.record_habit
    }
}

@StringRes
fun RecordType.title(): Int {
    return when (this) {
        RecordType.DAILY -> R.string.daily_title
        RecordType.EXERCISE -> R.string.exercise_title
//        RecordType.SCHEDULE -> R.string.schedule_title
        RecordType.HABIT -> R.string.habit_title
    }
}

fun RecordType.body(): Int {
    return when (this) {
        RecordType.DAILY -> R.string.daily_body
        RecordType.EXERCISE -> R.string.exercise_body
//        RecordType.SCHEDULE -> R.string.schedule_body
        RecordType.HABIT -> R.string.habit_body
    }
}

// 전체 값들에서
