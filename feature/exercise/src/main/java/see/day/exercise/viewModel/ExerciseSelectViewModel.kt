package see.day.exercise.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import see.day.analytics.AnalyticsEvent
import see.day.analytics.AnalyticsLogger
import see.day.analytics.types.WriteType
import javax.inject.Inject

@HiltViewModel
class ExerciseSelectViewModel @Inject constructor(
    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    fun writeExerciseRecordCancelLog() {
        analyticsLogger.writeRecordLog(AnalyticsEvent.WriteRecord("exercise", WriteType.CANCEL))
    }

    fun writeExerciseRecordDetailLog() {
        analyticsLogger.writeRecordLog(AnalyticsEvent.WriteRecord("exercise", WriteType.DETAIL))
    }
}
