package record.daily.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import record.daily.android.timber.ClassNameCustomTimber
import timber.log.Timber

@HiltAndroidApp
class RecordApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(ClassNameCustomTimber())
        }
    }
}
