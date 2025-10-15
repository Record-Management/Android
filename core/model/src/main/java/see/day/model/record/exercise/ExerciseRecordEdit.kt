package see.day.model.record.exercise

data class ExerciseRecordEdit(
    val recordId: String,
    val exerciseType: ExerciseType,
    val caloriesBurned: String,
    val exerciseTimeMinutes: String,
    val stepCount: String,
    val weight: String,
    val dailyNote: String,
    val imageUrls: List<String>,
    val recordTime: String
)
