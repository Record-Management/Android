package see.day.analytics

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsLogger @Inject constructor(){
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun log(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.name) {

        }
    }

    fun writeRecordLog(event: AnalyticsEvent.WriteRecord) {
        firebaseAnalytics.logEvent(event.name) {

        }
    }
}
