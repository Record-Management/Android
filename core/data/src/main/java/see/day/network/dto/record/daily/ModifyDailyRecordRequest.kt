package see.day.network.dto.record.daily

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class ModifyDailyRecordRequest(
    val content: String,
    val emotion: String,
    val imageUrls: List<String>
) {
    fun toRequestBody() = Json.encodeToString(
        this
    ).toRequestBody("application/json".toMediaTypeOrNull())
}
