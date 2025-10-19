package see.day.habit.state

import see.day.model.record.habit.HabitType

sealed interface HabitDetailUiEvent {
    data class OnHabitTypeChanged(val habitType: HabitType) : HabitDetailUiEvent
    data class OnMemoChanged(val memo: String) : HabitDetailUiEvent
    data class OnNotificationEnabledChanged(val enabled: Boolean) : HabitDetailUiEvent
    data class OnAlertTimeChanged(val hour: Int, val minute: Int) : HabitDetailUiEvent
    data object OnSaveRecord : HabitDetailUiEvent
    data class DeleteRecord(val recordId: String) : HabitDetailUiEvent
}
