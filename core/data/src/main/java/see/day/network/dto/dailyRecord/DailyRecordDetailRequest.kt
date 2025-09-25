package see.day.network.dto.dailyRecord

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class DailyRecordDetailRequest(
    val content: String,
    val emotion: String,
    val recordDate: String,
    val recordTime: String,
    val imageUrls: List<String>
) {
    fun toRequestBody() = Json.encodeToString(
        this
    ).toRequestBody("application/json".toMediaTypeOrNull())
}
