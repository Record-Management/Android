package see.day.model.user

import see.day.model.login.SocialType
import see.day.model.record.RecordType

data class User(
    val id: String,
    val name: String,
    val nickname: String,
    val email: String,
    val socialType: SocialType,
    val mainRecordType: RecordType,
    val birthDate: String,
    val goalDays: Int,
    val onboardingCompleted: Boolean,
    val createdAt: String
)
