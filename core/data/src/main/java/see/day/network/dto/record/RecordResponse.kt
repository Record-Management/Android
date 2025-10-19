package see.day.network.dto.record

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import see.day.model.calendar.DailyRecordDetail
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.calendar.HabitRecordDetail
import see.day.model.calendar.RecordDetail
import see.day.model.record.RecordType
import see.day.model.record.daily.DailyEmotion
import see.day.model.record.exercise.ExerciseType
import see.day.model.record.habit.HabitType
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class RecordResponse {
    abstract val id: String
    abstract val type: String
    abstract val recordDate: String
    abstract val recordTime: String
    abstract val createdAt: String
    abstract val updatedAt: String

    abstract fun toModel(): RecordDetail
}

@Serializable
@SerialName("DAILY")
data class DailyRecordResponse(
    override val id: String,
    override val type: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val recordDate: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val recordTime: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val createdAt: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val updatedAt: String,
    val imageUrls: List<String>,
    val emotion: String,
    val content: String
) : RecordResponse() {

    fun toDailyRecord() : DailyRecordDetail {
        return DailyRecordDetail(
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

    override fun toModel(): RecordDetail {
        return toDailyRecord()
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
    override val recordTime: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val createdAt: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val updatedAt: String,
    val exerciseType: String,
    val imageUrls: List<String>,
    val exerciseTimeMinutes: Int? = null,
    val stepCount: Int? = null,
    val caloriesBurned: Int? = null,
    val weight: Float? = null,
    val dailyNote: String
) : RecordResponse() {

    fun toExerciseRecord() : ExerciseRecordDetail {
        return ExerciseRecordDetail(
            id = id,
            type = RecordType.valueOf(type),
            recordDate = recordDate,
            createdAt = createdAt,
            updatedAt = updatedAt,
            recordTime = recordTime,
            exerciseType = ExerciseType.valueOf(exerciseType),
            imageUrls = imageUrls,
            exerciseTimeMinutes = exerciseTimeMinutes?.toString() ?: "",
            stepCount = stepCount?.toString() ?: "",
            weight = weight?.toString() ?: "",
            caloriesBurned = caloriesBurned?.toString() ?: "",
            dailyNote = dailyNote
        )
    }

    override fun toModel(): RecordDetail {
        return toExerciseRecord()
    }
}

@Serializable
@SerialName("HABIT")
data class HabitRecordResponse(
    override val id: String,
    override val type: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val recordDate: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val recordTime: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val createdAt: String,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    override val updatedAt: String,
    val habitType: String,
    val notificationEnabled: Boolean,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val notificationTime: String? = null,
    val memo: String? = null,
    val isCompleted: Boolean
) : RecordResponse() {

    fun toHabitRecord() : HabitRecordDetail {
        return HabitRecordDetail(
            id = id,
            type = RecordType.valueOf(type),
            recordDate = recordDate,
            createdAt = createdAt,
            updatedAt = updatedAt,
            recordTime = recordTime,
            habitType = HabitType.valueOf(habitType),
            notificationEnabled = notificationEnabled,
            notificationTime = notificationTime ?: "10:00",
            memo = memo ?: "",
            isCompleted = isCompleted
        )
    }

    override fun toModel(): RecordDetail {
        return toHabitRecord()
    }

}
