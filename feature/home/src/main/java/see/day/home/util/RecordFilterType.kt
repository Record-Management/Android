package see.day.home.util

import androidx.annotation.DrawableRes
import see.day.ui.R

enum class RecordFilterType(@DrawableRes val iconRes: Int) {
    ALL(R.drawable.filter_all),
    DAILY(R.drawable.filter_daily),
    EXERCISE(R.drawable.filter_exercise),
    SCHEDULE(R.drawable.filter_schedule),
    HABIT(R.drawable.filter_habit)
}
