package see.day.model.goal

import see.day.model.record.RecordType

data class NewGoal(
    val recordType: RecordType,
    val goalDays: Int
)
