package see.day.model.user

import see.day.model.record.RecordType

data class OnboardingComplete(
    val nickname: String,
    val mainRecordType: RecordType,
    val birthDate: String,
    val goalDays: Int,
    val notificationEnabled: Boolean
) {

}
