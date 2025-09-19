package see.day.network.decoder

import android.annotation.SuppressLint
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

/**
 * [2020, 9, 13] -> "2020-09-13"
 * [2025, 9, 13, 6, 41, 58] -> "2025-09-13:06:41:58"
 * [15, 21] -> "15:21"
 */
object FlexibleDateTimeArraySerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FlexibleDateTimeArray", PrimitiveKind.STRING)

    @SuppressLint("DefaultLocale")
    override fun deserialize(decoder: Decoder): String {
        val jsonArray = (decoder as JsonDecoder).decodeJsonElement() as JsonArray

        return when (jsonArray.size) {
            2 -> { // [hour, minute]
                val hour = jsonArray[0].jsonPrimitive.int
                val minute = jsonArray[1].jsonPrimitive.int
                String.format("%02d:%02d", hour, minute)
            }
            3 -> { // [year, month, day]
                val year = jsonArray[0].jsonPrimitive.int
                val month = jsonArray[1].jsonPrimitive.int
                val day = jsonArray[2].jsonPrimitive.int
                String.format("%04d-%02d-%02d", year, month, day)
            }
            6 -> { // [year, month, day, hour, minute, second]
                val year = jsonArray[0].jsonPrimitive.int
                val month = jsonArray[1].jsonPrimitive.int
                val day = jsonArray[2].jsonPrimitive.int
                val hour = jsonArray[3].jsonPrimitive.int
                val minute = jsonArray[4].jsonPrimitive.int
                val second = jsonArray[5].jsonPrimitive.int
                String.format("%04d-%02d-%02d:%02d:%02d:%02d", year, month, day, hour, minute, second)
            }
            else -> error("Unexpected date array length: ${jsonArray.size}")
        }
    }

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }
}
