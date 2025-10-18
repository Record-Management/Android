package see.day.designsystem.util

import see.day.designsystem.R
import see.day.model.record.habit.HabitType

val HabitType.getIconRes: Int
    get() = when (this) {
        HabitType.WATER_DRINKING -> R.drawable.habit_water_drinking
        HabitType.WALKING -> R.drawable.habit_walking
        HabitType.READING -> R.drawable.habit_reading
        HabitType.SAVING -> R.drawable.habit_saving
        HabitType.TAKE_MEDICINE -> R.drawable.habit_take_medicine
        HabitType.EARLY_RISING -> R.drawable.habit_early_rising
        HabitType.STRETCHING -> R.drawable.habit_stratching
        HabitType.EXERCISE -> R.drawable.habit_exercise
        HabitType.NO_DRINKING -> R.drawable.habit_no_drinking
        HabitType.NO_SMOKING -> R.drawable.habit_no_smoking
    }
