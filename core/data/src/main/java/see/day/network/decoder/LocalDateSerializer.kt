package see.day.network.decoder

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
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
            ?: return LocalDate.parse(decoder.decodeString())

        return when (val element = jsonDecoder.decodeJsonElement()) {
            is JsonPrimitive -> LocalDate.parse(element.content)
            is JsonArray -> {
                if (element.size != 3) {
                    throw SerializationException("LocalDate array must contain exactly 3 elements [year, month, day]")
                }
                val year = element[0].jsonPrimitive.int
                val month = element[1].jsonPrimitive.int
                val day = element[2].jsonPrimitive.int
                LocalDate.of(year, month, day)
            }

            else -> throw SerializationException("Unsupported JSON type for LocalDate: ${element::class.simpleName}")
        }
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }
}
