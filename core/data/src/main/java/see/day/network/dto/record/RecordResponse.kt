package see.day.network.dto.record

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import see.day.model.calendar.DetailDailyRecord
import see.day.model.calendar.DetailExerciseRecord
import see.day.model.calendar.DetailRecord
import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class RecordResponse {
    abstract val id: String
    abstract val type: String
    abstract val recordDate: String
    abstract val createdAt: String
    abstract val updatedAt: String

    abstract fun toModel(): DetailRecord
}

@Serializable
@SerialName("DAILY")
data class DailyRecordResponse(
    override val id: String,
    override val type: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val recordDate: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recordTime: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val createdAt: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val updatedAt: String,
    val imageUrls: List<String>,
    val emotion: String,
    val content: String
) : RecordResponse() {

    override fun toModel(): DetailRecord {
        return DetailDailyRecord(
            id = id,
            type = RecordType.valueOf(type),
            recordDate = recordDate,
            recordTime = recordTime,
            createdAt = createdAt,
            updatedAt = updatedAt,
            imageUrls = imageUrls,
            emotion = DailyEmotion.valueOf(emotion),
            content = content
        )
    }
}

@Serializable
@SerialName("EXERCISE")
data class ExerciseRecordResponse(
    override val id: String,
    override val type: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val recordDate: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val recordTime: String? = null,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val createdAt: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val updatedAt: String,
    val imageUrls: List<String>,
    val exerciseTimeMinutes: Int,
    val stepCount: Int,
    val weight: Float,
    val dailyNote: String
) : RecordResponse() {

    override fun toModel(): DetailRecord {
        return DetailExerciseRecord(
            id = id,
            type = RecordType.valueOf(type),
            recordDate = recordDate,
            createdAt = createdAt,
            updatedAt = updatedAt,
            recordTime = recordTime,
            imageUrls = imageUrls,
            exerciseTimeMinutes = exerciseTimeMinutes,
            stepCount = stepCount,
            weight = weight,
            dailyNote = dailyNote
        )
    }
}


