package see.day.network.dto.login

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class LoginRequest(
    val socialType: String,
    val accessToken: String
) {
    fun toRequestBody() = Json.encodeToString(this).toRequestBody()
}
