package see.day.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import see.day.model.goal.TreeStage
import see.day.model.record.RecordType
import see.day.network.decoder.FlexibleDateTimeArraySerializer

@Serializable
data class FullUserResponse(
    val id: String,
    val name: String,
    val nickname: String,
    val email: String? = null,
    val socialType: String,
    val mainRecordType: RecordType? = null,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val birthDate: String? = null,
    val goalDays: Int? ,
    val onboardingCompleted: Boolean,
    @Serializable(with = FlexibleDateTimeArraySerializer::class)
    val createdAt: String,
    @SerialName("currentTreeStage")
    val treeStage: TreeStage?
)
