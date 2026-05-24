package see.day.schedule.state

import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.model.schedule.ScheduleInput
import see.day.model.schedule.SchedulePaletteColor
import java.time.LocalDate

data class ScheduleDetailUiState(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val alertType: AlertTime,
    val notificationCustomHours: Int?,
    val notificationCustomMinutes: Int?,
    val repeatType: RepeatTime,
    val repeatEndsOn: LocalDate?,
    val location: String,
    val color: SchedulePaletteColor,
    val memo: String,
    val editMode: EditMode
) {
    sealed class EditMode {
        data object Create : EditMode()
        data class Edit(val originalSchedule: ScheduleInput, val scheduleId: String) : EditMode()
    }

    val canSubmit: Boolean = if(title.isBlank()) {
        false
    } else {
        when (editMode) {
            is EditMode.Create -> true
            is EditMode.Edit -> {
                isEditing()
            }
        }
    }

    fun isEditing(): Boolean = when(editMode) {
        is EditMode.Create -> this != init
        is EditMode.Edit -> {
            val origin = editMode.originalSchedule
            title != origin.title ||
                    startDate != origin.startDate ||
                    endDate != origin.endDate ||
                    alertType != origin.notificationType ||
                    notificationCustomHours != origin.notificationCustomHours ||
                    notificationCustomMinutes != origin.notificationCustomMinutes ||
                    repeatType != origin.repeatType ||
                    repeatEndsOn != origin.repeatEndsOn ||
                    location != origin.location ||
                    color != origin.color ||
                    memo != origin.memo
        }
    }

    companion object {
        val init = ScheduleDetailUiState(
            title = "",
            startDate = LocalDate.now(),
            endDate = LocalDate.now(),
            alertType = AlertTime.NONE,
            notificationCustomHours = 9,
            notificationCustomMinutes = 0,
            repeatType = RepeatTime.NONE,
            repeatEndsOn = null,
            location = "",
            color = SchedulePaletteColor.ORANGE,
            memo = "",
            editMode = EditMode.Create
        )
    }
}

