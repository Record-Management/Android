package see.day.analytics

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import see.day.analytics.types.GoalSettingType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsLogger @Inject constructor(){
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun log(event: AnalyticsEvent) {
        if(!BuildConfig.DEBUG) {
            firebaseAnalytics.logEvent(event.name) {
            }
        }
    }

    fun writeRecordLog(event: AnalyticsEvent.WriteRecord) {
        if(!BuildConfig.DEBUG) {
            firebaseAnalytics.logEvent(event.name) {

            }
        }
    }

    fun goalSettingLog(goalSettingType: GoalSettingType, recordType: String, goalDays: Int) {
        if(!BuildConfig.DEBUG) {
            firebaseAnalytics.logEvent(goalSettingType.name.lowercase()) {
                param("recordType", recordType)
                param("goalDays", goalDays.toString())
            }
        }
    }
}
