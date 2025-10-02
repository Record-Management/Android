package see.day.network.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

@Serializable
data class CommonResponse<T>(
    val statusCode: Int,
    val code: String,
    val message: String,
    val data: T? = null
)

inline fun <reified T> toResponseBody(request: CommonResponse<T>) = Json.encodeToString(
    serializer<CommonResponse<T>>(),
    request
).toResponseBody("application/json".toMediaTypeOrNull())
