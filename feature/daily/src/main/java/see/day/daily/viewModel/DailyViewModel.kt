package see.day.daily.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import see.day.analytics.AnalyticsEvent
import see.day.analytics.AnalyticsLogger
import see.day.analytics.types.WriteType
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val analyticsLogger: AnalyticsLogger
): ViewModel() {

    fun writeRecordCancelLog() {
        analyticsLogger.writeRecordLog(AnalyticsEvent.WriteRecord("daily", WriteType.CANCEL))
    }

    fun writeRecordDetailLog() {
        analyticsLogger.writeRecordLog(AnalyticsEvent.WriteRecord("daily", WriteType.DETAIL))
    }
}
