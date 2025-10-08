package see.day.network.dto.record.exercise

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

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
) {
    fun toRequestBody() = Json.encodeToString(
        this
    ).toRequestBody("application/json".toMediaTypeOrNull())
}
