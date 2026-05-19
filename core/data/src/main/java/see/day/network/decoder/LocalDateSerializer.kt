package see.day.network.decoder

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import java.time.LocalDate

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate {
        val jsonDecoder = decoder as? JsonDecoder
        return when (val element = jsonDecoder?.decodeJsonElement()) {
            is JsonPrimitive -> LocalDate.parse(element.content)
            is JsonArray -> {
                val year = element[0].jsonPrimitive.int
                val month = element[1].jsonPrimitive.int
                val day = element[2].jsonPrimitive.int
                LocalDate.of(year, month, day)
            }
            else -> LocalDate.parse(decoder.decodeString())
        }
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }
}
