package see.day.goal.state.reset

import see.day.model.record.RecordType

sealed interface ResetGoalUiEvent {
    data object OnClickBack: ResetGoalUiEvent
    data class SetRecordType(val recordType: RecordType) : ResetGoalUiEvent
    data class SetGoalDays(val goalDays: Int) : ResetGoalUiEvent
}
