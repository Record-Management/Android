package see.day.schedule.state

import androidx.compose.ui.graphics.Color
import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import java.time.LocalDate

sealed interface ScheduleDetailUiEvent {
    data class OnTitleChanged(val title: String) : ScheduleDetailUiEvent
    data class OnStartDateChanged(val startDate: LocalDate) : ScheduleDetailUiEvent
    data class OnEndDateChanged(val endDate: LocalDate) : ScheduleDetailUiEvent
    data class OnAlertTimeChanged(val alertTime: AlertTime, val hour: Int, val minute: Int) : ScheduleDetailUiEvent
    data class OnRepeatTypeChanged(val repeatTime: RepeatTime, val repeatEndTime: LocalDate?) : ScheduleDetailUiEvent
    data class OnLocationChanged(val location: String) : ScheduleDetailUiEvent
    data class OnColorChanged(val color: Color) : ScheduleDetailUiEvent
    data class OnMemoChanged(val memo: String) : ScheduleDetailUiEvent
}
