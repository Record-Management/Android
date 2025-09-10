package see.day.network.dto.user

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class OnboardingCompleteRequest(
    val nickname: String,
    val mainRecordType: String,
    val birthDate: String,
    val goalDays: Int,
    val notificationEnabled: Boolean
) {
    fun toRequestBody() = Json.encodeToString(
        this
    ).toRequestBody("application/json".toMediaTypeOrNull())
}
