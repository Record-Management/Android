package see.day.network.dto.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
) {
    fun toRequestBody() = Json.encodeToString(
        this
    ).toRequestBody("application/json".toMediaTypeOrNull())
}
