package see.day.designsystem.util

import see.day.designsystem.R
import see.day.model.record.exercise.ExerciseType

val ExerciseType.getIconRes : Int
    get() = when(this) {
        ExerciseType.BASEBALL -> R.drawable.exercise_baseball
        ExerciseType.BASKETBALL -> R.drawable.exercise_basketball
        ExerciseType.CYCLING -> R.drawable.exercise_cycling
        ExerciseType.GOLF -> R.drawable.exercise_golf
        ExerciseType.RUNNING -> R.drawable.exercise_running
        ExerciseType.SOCCER -> R.drawable.exercise_soccer
        ExerciseType.SWIMMING -> R.drawable.exercise_swimming
        ExerciseType.TENNIS -> R.drawable.exercise_tennis
        ExerciseType.WEIGHTS -> R.drawable.exercise_weights
        ExerciseType.YOGA -> R.drawable.exercise_yoga
    }
