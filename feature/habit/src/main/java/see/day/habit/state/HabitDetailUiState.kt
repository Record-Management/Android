package see.day.habit.state

import see.day.model.record.habit.HabitRecordInput
import see.day.model.record.habit.HabitType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter

data class HabitDetailUiState(
    val habitType: HabitType,
    val notificationEnabled: Boolean,
    val hour: Int,
    val minute: Int,
    val memo: String,
    val recordDate: String,
    val editMode: EditMode
) {
    sealed class EditMode {
        data object Create : EditMode()
        data class Edit(val originalRecord: HabitRecordInput, val recordId: String) : EditMode()
    }

    val canSubmit: Boolean = when(editMode) {
        is EditMode.Create -> {
            true
        }
        is EditMode.Edit -> {
            isEditing()
        }
    }

    fun isEditing(): Boolean = when (editMode) {
        is EditMode.Create -> {
            notificationEnabled || memo.isNotEmpty()
        }

        is EditMode.Edit -> {
            val origin = editMode.originalRecord
            (memo != origin.memo || notificationEnabled != origin.notificationEnabled || hour != origin.notificationHour || minute != origin.notificationMinute || habitType != origin.habitType)
        }
    }

    companion object {
        val init = HabitDetailUiState(
            habitType = HabitType.SAVING,
            notificationEnabled = false,
            hour = 10,
            minute = 0,
            memo = "",
            recordDate = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)).formatDate(),
            editMode = EditMode.Create
        )
    }
}
