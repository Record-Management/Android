package see.day.network.dto.record

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class RecordResponse {
    abstract val id: String
    abstract val recordDate: String
    abstract val recordTime: String
    abstract val createdAt: String
    abstract val updatedAt: String
    abstract val imageUrls : List<String>
}
