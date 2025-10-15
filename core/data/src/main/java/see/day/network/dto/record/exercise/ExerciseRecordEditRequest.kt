package see.day.network.dto.record.exercise

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseRecordEditRequest(
    val exerciseType: String,
    val caloriesBurned: Int?,
    val exerciseTimeMinutes: Int?,
    val stepCount: Int?,
    val weight: Float?,
    val dailyNote: String,
    val imageUrls: List<String>,
    val recordTime: String
)
