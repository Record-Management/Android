package see.day.notification.util

import androidx.annotation.DrawableRes
import see.day.model.notification.NotificationType
import see.day.model.record.RecordType
import see.day.notification.R

@DrawableRes
fun NotificationType.getIcon() : Int {
    return when(this) {
        NotificationType.DAILY_RECORD_REMINDER -> R.drawable.ic_daily_notification
        NotificationType.EXERCISE_REMINDER -> R.drawable.ic_exercise_notification
        NotificationType.HABIT_REMINDER -> R.drawable.ic_habit_notification
        NotificationType.GOAL_SETTING_REMINDER -> R.drawable.ic_goal_notification
        else -> R.drawable.ic_goal_notification
    }
}

fun NotificationType.toRecordType() : RecordType? {
    return when(this) {
        NotificationType.DAILY_RECORD_REMINDER -> RecordType.DAILY
        NotificationType.EXERCISE_REMINDER -> RecordType.EXERCISE
        NotificationType.HABIT_REMINDER -> RecordType.HABIT
        else -> null
    }
}
