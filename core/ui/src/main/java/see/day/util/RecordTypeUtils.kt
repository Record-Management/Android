package see.day.util

import androidx.annotation.DrawableRes
import see.day.model.record.RecordType
import see.day.ui.R

@DrawableRes
fun RecordType.getBigIcon(): Int {
    return when (this) {
        RecordType.DAILY -> see.day.designsystem.R.drawable.record_daily
        RecordType.EXERCISE -> see.day.designsystem.R.drawable.record_exercise
        RecordType.SCHEDULE -> see.day.designsystem.R.drawable.record_schedule
        RecordType.HABIT -> see.day.designsystem.R.drawable.record_habit
    }
}

@DrawableRes
fun RecordType.getIcon() : Int {
    return when(this) {
        RecordType.DAILY -> R.drawable.filter_daily
        RecordType.EXERCISE -> R.drawable.filter_exercise
        RecordType.HABIT -> R.drawable.filter_habit
        RecordType.SCHEDULE -> R.drawable.filter_schedule
    }
}

@DrawableRes
fun RecordType.getGrayIcon() : Int {
    return when(this) {
        RecordType.DAILY -> R.drawable.filter_daily_gray
        RecordType.EXERCISE -> R.drawable.filter_exercise_gray
        RecordType.HABIT -> R.drawable.filter_habit_gray
        RecordType.SCHEDULE -> R.drawable.filter_schedule_gray
    }
}
