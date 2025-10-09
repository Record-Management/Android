package see.day.model.record.exercise

import see.day.model.time.DateTimeFormatter

data class ExerciseRecordInput(
    val exerciseType: ExerciseType,
    val dailyNote: String,
    val recordDate: DateTimeFormatter,
    val caloriesBurned: String,
    val exerciseTimeMinutes: String,
    val stepCount: String,
    val weight: String,
    val imageUrls: List<String>
)
