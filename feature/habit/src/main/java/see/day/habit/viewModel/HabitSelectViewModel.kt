package see.day.habit.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import see.day.analytics.AnalyticsEvent
import see.day.analytics.AnalyticsLogger
import see.day.analytics.types.WriteType
import javax.inject.Inject

@HiltViewModel
class HabitSelectViewModel @Inject constructor(
    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    fun writeHabitRecordDetailLog() {
        analyticsLogger.writeRecordLog(AnalyticsEvent.WriteRecord("habit", WriteType.DETAIL))
    }

    fun writeHabitRecordCancelLog() {
        analyticsLogger.writeRecordLog(AnalyticsEvent.WriteRecord("habit", WriteType.CANCEL))
    }
}
