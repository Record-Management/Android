package see.day.network.dto.record.exercise

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseRecordInputRequest(
    val exerciseType: String,
    val dailyNote: String,
    val recordDate: String,
    val recordTime: String,
    val caloriesBurned: Int?,
    val exerciseTimeMinutes: Int?,
    val stepCount: Int?,
    val weight: Float?,
    val imageUrls: List<String>
)
